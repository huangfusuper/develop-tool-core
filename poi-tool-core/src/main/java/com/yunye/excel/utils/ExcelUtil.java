package com.yunye.excel.utils;

import com.sun.istack.internal.Nullable;
import com.yunye.common.enums.poi.DataTypeEnum;
import com.yunye.common.exception.ExceptionModel;
import com.yunye.common.exception.PoiToolException;
import com.yunye.common.utils.DateUtil;
import com.yunye.common.utils.ReflectUtils;
import com.yunye.common.utils.RuleCheckUtil;
import com.yunye.excel.Import.conf.ImportExcelProperties;
import com.yunye.excel.definition.DefaultImportExcelEntityDefinition;
import com.yunye.excel.definition.base.BaseImportExcelEntityDefinition;
import com.yunye.excel.definition.parse.DefaultImportFieldDefinitionParse;
import com.yunye.excel.definition.parse.FieldDefinitionParse;
import com.yunye.excel.model.ClassEntityDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

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
public class ExcelUtil {

    private static final Map<String, ClassEntityDefinition> DEFINITION_POOL = new ConcurrentHashMap<>(8);
    private static final ImportExcelProperties DEFAULT_IMPORT_EXCEL_PROPERTIES = new ImportExcelProperties(null,0,true);

    /**
     * 解析Excel
     * @param excelEntityClass 对应的实体类对象
     * @param <T> 返回类型
     * @return 返回Excel数据
     */
    public static <T> List<T> importExcel(InputStream excelIn, Class<T> excelEntityClass){
        return importExcel(excelIn,excelEntityClass,null);
    }

    /**
     * 解析Excel
     * @param excelEntityClass 对应的实体类对象
     * @param importExcelProperties 导入Excel的配置
     * @param <T> 返回类型
     * @return 返回Excel数据
     */
    public static <T> List<T> importExcel(InputStream excelIn, Class<T> excelEntityClass,
                                          @Nullable ImportExcelProperties importExcelProperties){
        return importExcel(excelIn,null,excelEntityClass,importExcelProperties,null,null);
    }
    /**
     * 解析Excel
     * @param excelEntityClass 对应的实体类对象
     * @param importExcelProperties 导入Excel的配置
     * @param importDefinitionParse 导入定义类对象
     * @param exportDefinitionParse 导出定义类对象
     * @param password 密码
     * @param <T> 返回类型
     * @return 返回Excel数据
     */
    public static <T> List<T> importExcel(InputStream excelIn, @Nullable String password ,
                                          Class<T> excelEntityClass,
                                          @Nullable ImportExcelProperties importExcelProperties,
                                          @Nullable FieldDefinitionParse importDefinitionParse,
                                          @Nullable FieldDefinitionParse exportDefinitionParse){

        RuleCheckUtil.objectNotNull(excelEntityClass,new ExceptionModel("30000001","Excel映射实体类对象不能为null!"));
        if(importExcelProperties == null){
            importExcelProperties = DEFAULT_IMPORT_EXCEL_PROPERTIES;
        }
        if(importDefinitionParse == null){
            importDefinitionParse = new DefaultImportFieldDefinitionParse(excelEntityClass);
        }

        if(exportDefinitionParse == null){
            exportDefinitionParse = new DefaultImportFieldDefinitionParse(excelEntityClass);
        }
        String className = excelEntityClass.getName();

        if(!DEFINITION_POOL.containsKey(className)){
            setPool(className,importExcelProperties,importDefinitionParse,exportDefinitionParse);
        }

        try {
            return parseExcel(excelIn,password,excelEntityClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PoiToolException(e);
        }


    }


    /**
     * 解析加密Excel
     * @param excelIn Excel文件流
     * @param passWord 文件密码
     * @param <T> 返回数据类型
     * @return 返回数据
     * @throws IOException 异常
     */
    private static <T> List<T> parseExcel(InputStream excelIn,String passWord,Class<T> excelEntityClass) throws IOException {
        String className = excelEntityClass.getName();
        Workbook workbook;
        if(StringUtils.isBlank(passWord)){
            workbook = WorkbookFactory.create(excelIn);
        }else{
            workbook = WorkbookFactory.create(excelIn,passWord);
        }
        //获取属性定义
        ClassEntityDefinition classEntityDefinition = DEFINITION_POOL.get(className);
        Map<String, BaseImportExcelEntityDefinition> importExcelEntityDefinition = classEntityDefinition.getImportFieldDefinitionParse();
        //获取读取Excel的初始配置
        ImportExcelProperties importExcelProperties = classEntityDefinition.getImportExcelProperties();
        //获取所有的sheet页
        List<Sheet> excelSheet = getExcelSheet(workbook, importExcelProperties);
        List<T> entityArray = new ArrayList<>(32);
        try {
            for (Sheet sheet : excelSheet) {
                getSheetRowsData(sheet,entityArray,importExcelProperties.getRowStartIndex(),
                        importExcelEntityDefinition,excelEntityClass);
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
     * @param startRowIndex 开始的行数
     * @param <T> 数据类型
     */
    private static <T> void getSheetRowsData(Sheet sheet,List<T> entityArray,Integer startRowIndex,
                                             Map<String, BaseImportExcelEntityDefinition> importExcelEntityDefinition,
                                             Class<T> entityClass) throws IllegalAccessException, InstantiationException {
        //为了防止数据多 反射获取满 将对应的字段加一个伪缓存
        Map<String, Field> fieldCache = new HashMap<>(8);
        int rowTotal = sheet.getPhysicalNumberOfRows();
        //逐行读取数据
        for (int rowIndex = startRowIndex; rowIndex < rowTotal; rowIndex++) {
            T t = entityClass.newInstance();
            Row row = sheet.getRow(rowIndex);
            //行内数据根据定义读取数据
            importExcelEntityDefinition.forEach((key,value) ->{
                if(!fieldCache.containsKey(key)){
                    Field field = ReflectUtils.getField(t, key);
                    fieldCache.put(key,field);
                }
                Field field = fieldCache.get(key);
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
    private static Object formatCellValue(Cell cell,BaseImportExcelEntityDefinition defaultImportExcelEntityDefinition,
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
    private static Object formatTextData(String textData,BaseImportExcelEntityDefinition defaultImportExcelEntityDefinition,
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
    private static List<Sheet> getExcelSheet(Workbook workbook,ImportExcelProperties importExcelProperties){
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
    private static List<Integer> getExcelAllSheetIndex(Workbook workbook){
        int numberOfSheets = workbook.getNumberOfSheets();
        List<Integer> sheetNumbers = new ArrayList<>(8);
        for (int i = 0; i < numberOfSheets; i++) {
            sheetNumbers.add(i);
        }
        return sheetNumbers;
    }

    /**
     * TODO exportDefinition还未编写
     * @param className 全限定名
     * @param importDefinitionParse 导入的定义
     * @param exportDefinitionParse 导出的定义
     */
    private static void setPool(String className,
                                ImportExcelProperties importExcelProperties,
                                FieldDefinitionParse importDefinitionParse,
                                FieldDefinitionParse exportDefinitionParse){

        ClassEntityDefinition classEntityDef = ClassEntityDefinition.builder()
                .className(className)
                .importExcelProperties(importExcelProperties)
                .importFieldDefinitionParse(importDefinitionParse.parsingImportExcelEntity())
                .exportFieldDefinitionParse(null)
                .build();
        DEFINITION_POOL.put(className,classEntityDef);
    }
}
