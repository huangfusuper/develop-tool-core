package com.yunye.excel.utils;

import com.yunye.common.annotations.poi.excel.ExcelImportField;
import com.yunye.common.enums.poi.DataTypeEnum;
import com.yunye.common.exception.ExceptionModel;
import com.yunye.common.exception.PoiToolException;
import com.yunye.common.utils.DateUtil;
import com.yunye.common.utils.ReflectUtils;
import com.yunye.common.utils.RuleCheckUtil;
import com.yunye.excel.Import.conf.ImportExcelProperties;
import com.yunye.excel.definition.base.BaseImportExcelEntityDefinition;
import com.yunye.excel.definition.parse.DefaultImportFieldDefinitionParse;
import com.yunye.excel.definition.parse.FieldDefinitionParse;
import com.yunye.excel.model.ClassEntityDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Excel的操作对象
 * @author huangfu
 */
public class ImportExcel<T> {

    private static final Map<String, ClassEntityDefinition> DEFINITION_POOL = new ConcurrentHashMap<>(8);
    private ImportExcelProperties importExcelProperties;
    private FieldDefinitionParse importDefinitionParse;
    private FieldDefinitionParse exportDefinitionParse;
    private InputStream excelIn;
    private String password;
    private Class<T> excelEntityClass;
    private Map<String, BaseImportExcelEntityDefinition> importExcelEntityDefinition;
    /**
     * 为了防止数据多 反射获取满 将对应的字段加一个伪缓存
     */
    private Map<String, Field> fieldCache = new HashMap<>(8);



    public ImportExcel(InputStream excelIn,Class<T> excelEntityClass) {
        this(excelIn,null, excelEntityClass);
    }

    public ImportExcel(InputStream excelIn,String password,Class<T> excelEntityClass) {
        this(null,null,null, excelIn, password, excelEntityClass);
    }

    public ImportExcel(ImportExcelProperties importExcelProperties, InputStream excelIn, Class<T> excelEntityClass) {
        this(importExcelProperties,null,null, excelIn, null, excelEntityClass);
    }

    public ImportExcel(ImportExcelProperties importExcelProperties, FieldDefinitionParse importDefinitionParse,
                       FieldDefinitionParse exportDefinitionParse, InputStream excelIn,
                       String password, Class<T> excelEntityClass) {


        this.importExcelProperties = importExcelProperties;
        this.importDefinitionParse = importDefinitionParse;
        this.exportDefinitionParse = exportDefinitionParse;
        this.excelIn = excelIn;
        this.password = password;
        this.excelEntityClass = excelEntityClass;
    }

    /**
     * 解析Excel
     * @return 返回Excel数据
     */
    public List<T> importExcel(){

        RuleCheckUtil.objectNotNull(excelEntityClass,new ExceptionModel("30000001","Excel映射实体类对象不能为null!"));
        if(importExcelProperties == null){
            importExcelProperties = new ImportExcelProperties(null,0,true);;
        }
        if(importDefinitionParse == null){
            importDefinitionParse = new DefaultImportFieldDefinitionParse(excelEntityClass);
        }

        if(exportDefinitionParse == null){
            exportDefinitionParse = new DefaultImportFieldDefinitionParse(excelEntityClass);
        }
        String className = excelEntityClass.getName();

        if(!DEFINITION_POOL.containsKey(className)){
            setPool();
        }

        try {
            return parseExcel();
        } catch (IOException e) {
            e.printStackTrace();
            throw new PoiToolException(e);
        }


    }


    /**
     * 解析加密Excel
     * @return 返回数据
     * @throws IOException 异常
     */
    private List<T> parseExcel() throws IOException {
        String className = excelEntityClass.getName();
        Workbook workbook;
        if(StringUtils.isBlank(password)){
            workbook = WorkbookFactory.create(excelIn);
        }else{
            workbook = WorkbookFactory.create(excelIn,password);
        }
        //获取属性定义
        ClassEntityDefinition classEntityDefinition = DEFINITION_POOL.get(className);
        importExcelEntityDefinition = classEntityDefinition.getImportFieldDefinitionParse();
        //获取读取Excel的初始配置
        importExcelProperties = classEntityDefinition.getImportExcelProperties();
        //获取所有的sheet页
        List<Sheet> excelSheet = getExcelSheet(workbook, importExcelProperties);
        List<T> entityArray = new ArrayList<>(32);
        try {
            for (Sheet sheet : excelSheet) {
                getSheetRowsData(sheet,entityArray);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return entityArray;
    }

    /**
     * 获取对应sheet内全部的数据
     * @param sheet 对应的sheet
     * @param entityArray 数据存放载体
     */
    private void getSheetRowsData(Sheet sheet,List<T> entityArray) throws IllegalAccessException, InstantiationException {
        Integer startRowIndex = importExcelProperties.getRowStartIndex();

        int rowTotal = sheet.getPhysicalNumberOfRows();
        //逐行读取数据
        for (int rowIndex = startRowIndex; rowIndex < rowTotal; rowIndex++) {
            T t = excelEntityClass.newInstance();
            Row row = sheet.getRow(rowIndex);
            //行内数据根据定义读取数据
            importExcelEntityDefinition.forEach((key,value) ->{
                if(!this.fieldCache.containsKey(key)){
                    Field field = ReflectUtils.getField(t, key);
                    this.fieldCache.put(key,field);
                }
                Field field = this.fieldCache.get(key);
                Integer columnIndex = value.getColumnIndex();
                //获取该属性对应的列
                Cell cell = row.getCell(columnIndex);
                Object cellValue = formatCellValue(cell, value, t, field);
                field.setAccessible(true);
                try {
                    field.set(t,cellValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new PoiToolException(e);
                }
            });
            entityArray.add(t);
        }
    }

    /**
     * 根据定义格式化 value
     * @param cell 对应列
     * @param defaultImportExcelEntityDefinition 类型定义
     * @param object 对应的类对象
     * @return 对应值
     */
    private Object formatCellValue(Cell cell,BaseImportExcelEntityDefinition defaultImportExcelEntityDefinition,
                                          Object object,Field field){
        Object value = null;
        String dataType = defaultImportExcelEntityDefinition.getDataTypeEnum().getDataType();
        //文本数据
        if(DataTypeEnum.TEXT.getDataType().equals(dataType)){
            String stringCellValue = cell.getStringCellValue();
            value = formatTextData(stringCellValue,defaultImportExcelEntityDefinition,object);
        }else if(DataTypeEnum.DATE.getDataType().equals(dataType)){
            value = cell.getDateCellValue();
            if (String.class.equals(field.getType())) {
                String dateFormat = defaultImportExcelEntityDefinition.getDateFormat();
                value = DateUtil.dateFormat((Date)value,dateFormat);
            }

        }else if(DataTypeEnum.FUNCTION.getDataType().equals(dataType)){
            value = cell.getCellFormula();
        }else if(DataTypeEnum.IMAGE.getDataType().equals(dataType)){
            //TODO 暂时没找到方法
        }else if(DataTypeEnum.NUMBER.getDataType().equals(dataType)){
            double numericCellValue = cell.getNumericCellValue();
            String numFormat = defaultImportExcelEntityDefinition.getNumFormat();
            DecimalFormat decimalFormat=new DecimalFormat(numFormat);
            String format = decimalFormat.format(numericCellValue);
            if(String.class.equals(field.getType())){
                value = format;
            }else if(Integer.class.equals(field.getType())){
                value = Integer.parseInt(format);
            }else if(Double.class.equals(field.getType())){
                value = Double.parseDouble(format);
            }else if(Float.class.equals(field.getType())){
                value = Float.parseFloat(format);
            }
        }
        return value;
    }

    /**
     * 根据映射的数据格式化真实数据
     * @param textData 真是数据
     * @param defaultImportExcelEntityDefinition 定义
     * @param object 对应的类对象
     * @return 返回格式化后的数据
     */
    private Object formatTextData(String textData,BaseImportExcelEntityDefinition defaultImportExcelEntityDefinition,
                                        Object object){
        if (StringUtils.isEmpty(textData)) {
            return textData;
        }

        Map<String, String> valueMap = defaultImportExcelEntityDefinition.getValueMap();
        if(valueMap != null){
            Set<Map.Entry<String, String>> entries = valueMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                if (Pattern.matches(entry.getKey(),textData)) {
                    return entry.getValue();
                }
            }
        }
        //执行映射方法
        String simpleConversionMethodName = defaultImportExcelEntityDefinition.getSimpleConversionMethodName();
        if(StringUtils.isNoneBlank(simpleConversionMethodName)){
            try {
                Method method = ReflectUtils.getMethod(object, simpleConversionMethodName, String.class);
                return method.invoke(object, textData);
            }catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new PoiToolException(new ExceptionModel("30000002",simpleConversionMethodName+"方法不存在！请检查映射类的映射方法是否正确！"));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new PoiToolException(e);
            }
        }
        //格式化数据
        if (defaultImportExcelEntityDefinition.getTrimStr()) {
            return textData.trim();
        }

        return textData;
    }

    /**
     * 获取一个Excel里面所有的sheet页
     * @param workbook 对应的工作
     * @param importExcelProperties 导入配置
     * @return 返回对应的sheet页
     */
    private List<Sheet> getExcelSheet(Workbook workbook,ImportExcelProperties importExcelProperties){
        List<Sheet> sheets = new ArrayList<>(8);
        //如果是查询所有的
        if (importExcelProperties.getIsAllSheet()) {
            importExcelProperties.setSheets(getExcelAllSheetIndex(workbook));
        }
        List<Integer> sheetIndex = importExcelProperties.getSheets();

        for (Integer sheet : sheetIndex) {
            sheets.add(workbook.getSheetAt(sheet));
        }
        return sheets;
    }


    /**
     * 查询全部的sheet页
     * @param workbook 工作
     * @return 返回所有的sheet页
     */
    private List<Integer> getExcelAllSheetIndex(Workbook workbook){
        int numberOfSheets = workbook.getNumberOfSheets();
        List<Integer> sheetNumbers = new ArrayList<>(8);
        for (int i = 0; i < numberOfSheets; i++) {
            sheetNumbers.add(i);
        }
        return sheetNumbers;
    }

    /**
     * TODO exportDefinition还未编写
     */
    private void setPool(){
        String className = excelEntityClass.getName();
        ClassEntityDefinition classEntityDef = ClassEntityDefinition.builder()
                .className(className)
                .importExcelProperties(importExcelProperties)
                .importFieldDefinitionParse(importDefinitionParse.parsingImportExcelEntity())
                .exportFieldDefinitionParse(null)
                .build();
        DEFINITION_POOL.put(className,classEntityDef);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User{
    @ExcelImportField(columnIndex = 0,replace = {"123.*:格式化后的数据"})
    private String id;
    @ExcelImportField(columnIndex = 1,dataTypeEnum = DataTypeEnum.NUMBER)
    private Integer age;

    public static void main(String[] args) throws FileNotFoundException {
        ImportExcel<User> userImportExcel = new ImportExcel<>(new FileInputStream("C:\\Users\\huangfu\\Desktop/qwe.xlsx"), User.class);
        List<User> users = userImportExcel.importExcel();
        users.forEach(e ->{
            System.out.println(e);
        });
    }
}
