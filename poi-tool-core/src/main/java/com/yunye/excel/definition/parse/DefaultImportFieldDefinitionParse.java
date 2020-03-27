package com.yunye.excel.definition.parse;

import com.yunye.common.annotations.poi.excel.ExcelDateFormat;
import com.yunye.common.annotations.poi.excel.ExcelImportField;
import com.yunye.common.utils.ReflectUtils;
import com.yunye.excel.definition.DefaultImportExcelEntityDefinition;
import com.yunye.excel.definition.parse.BaseFieldDefinitionParse;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的导入解析器
 * @author huangfu
 */
public class DefaultImportFieldDefinitionParse extends BaseFieldDefinitionParse {
    private Class<?> entityClass;

    public DefaultImportFieldDefinitionParse(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Map<String, DefaultImportExcelEntityDefinition> parsingImportExcelEntity() {
        Map<String,DefaultImportExcelEntityDefinition> map = new ConcurrentHashMap<>(8);
        //获取所有的属性字段
        List<Field> allField = ReflectUtils.getAllField(entityClass);
        allField.forEach( field -> {
            if(field.isAnnotationPresent(ExcelImportField.class) || field.isAnnotationPresent(ExcelDateFormat.class)){
                DefaultImportExcelEntityDefinition defaultImportExcelEntityDefinition = new DefaultImportExcelEntityDefinition();
                String fieldName = field.getName();
                parseExcelImportField(field.getAnnotation(ExcelImportField.class),defaultImportExcelEntityDefinition);
                parseExcelDateFormat(field.getAnnotation(ExcelDateFormat.class),defaultImportExcelEntityDefinition);
                map.put(fieldName,defaultImportExcelEntityDefinition);
            }

        });
        return map;
    }

    /**
     * 解析 ExcelImportField 注解
     * @param excelImportField 对应注解
     */
    private void parseExcelImportField(ExcelImportField excelImportField,DefaultImportExcelEntityDefinition definition){
        if(excelImportField != null){
            definition.setColumnIndex(excelImportField.columnIndex());
            definition.setDataTypeEnum(excelImportField.dataTypeEnum());
            definition.setTrimStr(excelImportField.trimStr());
            String[] valueMapArrayData = excelImportField.replace();
            if (valueMapArrayData.length > 0) {
                parseReplace(valueMapArrayData,definition);
            }
            definition.setSimpleConversionMethodName(excelImportField.simpleConversionMethodName());
            definition.setNumFormat(excelImportField.numFormat());
        }
    }

    /**
     * 分割数组  将数组分割为Map
     * @param sourceData 元数据
     */
    private void parseReplace(String[] sourceData,DefaultImportExcelEntityDefinition defaultImportExcelEntityDefinition){
        if (defaultImportExcelEntityDefinition.getValueMap() == null){
            defaultImportExcelEntityDefinition.setValueMap(new ConcurrentHashMap<>(8));
            defaultImportExcelEntityDefinition.setValueMap(new ConcurrentHashMap<>(8));
        }
        for (String sourceDatum : sourceData) {
            String[] childSourceData = sourceDatum.split(":");
            defaultImportExcelEntityDefinition.getValueMap().put(childSourceData[0], childSourceData[1]);
        }
    }

    /**
     * 解析 ExcelDateFormat 注解
     * @param excelDateFormat 对应注解
     */
    private void parseExcelDateFormat(ExcelDateFormat excelDateFormat,DefaultImportExcelEntityDefinition defaultImportExcelEntityDefinition){
        if (excelDateFormat != null) {
            defaultImportExcelEntityDefinition.setDateFormat(excelDateFormat.value());
        }

    }
}
