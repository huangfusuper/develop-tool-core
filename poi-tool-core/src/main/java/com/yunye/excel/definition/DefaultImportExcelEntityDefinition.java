package com.yunye.excel.definition;

import com.yunye.common.annotations.poi.excel.ExcelDateFormat;
import com.yunye.common.annotations.poi.excel.ExcelImportField;
import com.yunye.common.enums.poi.DataTypeEnum;
import com.yunye.common.utils.ReflectUtils;
import com.yunye.excel.Import.conf.ImportExcelProperties;
import com.yunye.excel.definition.base.BaseExcelEntityDefinition;
import com.yunye.excel.definition.base.ExcelEntityDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的解析器
 * @author huangfu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class DefaultImportExcelEntityDefinition extends BaseExcelEntityDefinition {
    private Class entityClass;

    /**
     * 列索引
     */
    private Integer columnIndex;
    /**
     * 时间类型
     */
    private String dateFormat = "yyyy-MM-dd";
    /**
     * 是否自动格式化内部数据
     */
    private Boolean trimStr;
    /**
     * 内部需要替换的数据
     */
    private Map<String,String> valueMap;
    /**
     * 简单的数据转换
     */
    private String simpleConversionMethodName;
    /**
     * 导入的数据类型
     */
    private DataTypeEnum dataTypeEnum;
    /**
     * 数字格式化的格式
     */
    private String numFormat;

    public DefaultImportExcelEntityDefinition(Class entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Map<String, ExcelEntityDefinition> parsingImportExcelEntity() {
        Map<String,ExcelEntityDefinition> map = new ConcurrentHashMap<>(8);
        //获取所有的属性字段
        List<Field> allField = ReflectUtils.getAllField(entityClass);
        allField.forEach( field -> {
            //TODO 这一块需要提取出来 this当作参数会出现问题 需要重新new才可以
            String fieldName = field.getName();
            parseExcelImportField(field.getAnnotation(ExcelImportField.class));
            parseExcelDateFormat(field.getAnnotation(ExcelDateFormat.class));
            map.put(fieldName,this);
        });
        return map;
    }

    /**
     * 解析 ExcelImportField 注解
     * @param excelImportField 对应注解
     */
    private void parseExcelImportField(ExcelImportField excelImportField){
        if(excelImportField != null){
            this.columnIndex = excelImportField.columnIndex();
            this.dataTypeEnum = excelImportField.dataTypeEnum();
            this.trimStr = excelImportField.trimStr();
            String[] valueMapArrayData = excelImportField.replace();
            if (valueMapArrayData.length > 0) {
                parseReplace(valueMapArrayData);
            }
            this.simpleConversionMethodName = excelImportField.simpleConversionMethodName();
            this.numFormat = excelImportField.numFormat();
        }
    }

    /**
     * 分割数组  将数组分割为Map
     * @param sourceData 元数据
     */
    private void parseReplace(String[] sourceData){
        if (valueMap == null){
            valueMap = new ConcurrentHashMap<>(8);
        }
        for (String sourceDatum : sourceData) {
            String[] childSourceData = sourceDatum.split(":");
            valueMap.put(childSourceData[0], childSourceData[1]);
        }
    }

    /**
     * 解析 ExcelDateFormat 注解
     * @param excelDateFormat 对应注解
     */
    private void parseExcelDateFormat(ExcelDateFormat excelDateFormat){
        if (excelDateFormat != null) {
            this.dateFormat = excelDateFormat.value();
        }

    }
}
