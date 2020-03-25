package com.yunye.excel.definition.base;

import com.yunye.common.enums.poi.DataTypeEnum;
import com.yunye.common.enums.poi.DateOpportunityEnum;
import com.yunye.excel.Import.conf.ImportExcelProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析器
 * @author huangfu
 */
public abstract class BaseExcelEntityDefinition implements ExcelEntityDefinition {
    /**
     * 定义字段与解析器的关系
     */
    private Map<String,ExcelEntityDefinition> definePool = new ConcurrentHashMap<>(8);
    /**
     * 导入的配置
     */
    private ImportExcelProperties importExcelProperties;
    /**
     * 列索引
     */
    private Integer columnIndex;

    /**
     * 事件类型
     */
    private String dateFormat = "yyyy-MM-dd";
    /**
     * 是否基于相同值合并同列的数据
     */
    private Boolean mergeVertical = false;
    /**
     * date抓换的时机  IMPORT 导入  EXPORT 导出  3.ALL
     */
    private DateOpportunityEnum dateOpportunity;
    /**
     * 是否自动格式化内部数据
     */
    private Boolean trimStr = false;
    /**
     * 内部需要替换的数据
     */
    private Map<String,String> valueMap;

    /**
     * 简单的数据转换
     */
    private String simpleConversionMethodName;

    /**
     * 导出的数据类型
     */
    private DataTypeEnum dataTypeEnum;
    /**
     * 数字格式化的格式
     */
    private String numFormat;

    /**
     * 列是否隐藏
     */
    private Boolean columnHidden = false;
    /**
     * 是否开启强调
     */
    private Boolean isEmphasize = false;
    /**
     * 强调的颜色
     */
    private String emphasizeColour;
    /**
     * 强调的方法名称
     */
    private String emphasizeMethodName;
    /**
     * 列宽
     */
    private Integer columnWidth;
    /**
     * 列高
     */
    private Integer columnHigh;
    /**
     * 是否需要边框
     */
    private Boolean frame = false;

    public Map<String, ExcelEntityDefinition> getDefinePool() {
        return definePool;
    }

    public void setDefinePool(Map<String, ExcelEntityDefinition> definePool) {
        this.definePool = definePool;
    }

    public ImportExcelProperties getImportExcelProperties() {
        return importExcelProperties;
    }

    public void setImportExcelProperties(ImportExcelProperties importExcelProperties) {
        this.importExcelProperties = importExcelProperties;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Boolean getMergeVertical() {
        return mergeVertical;
    }

    public void setMergeVertical(Boolean mergeVertical) {
        this.mergeVertical = mergeVertical;
    }

    public DateOpportunityEnum getDateOpportunity() {
        return dateOpportunity;
    }

    public void setDateOpportunity(DateOpportunityEnum dateOpportunity) {
        this.dateOpportunity = dateOpportunity;
    }

    public Boolean getTrimStr() {
        return trimStr;
    }

    public void setTrimStr(Boolean trimStr) {
        this.trimStr = trimStr;
    }

    public Map<String, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, String> valueMap) {
        this.valueMap = valueMap;
    }

    public String getSimpleConversionMethodName() {
        return simpleConversionMethodName;
    }

    public void setSimpleConversionMethodName(String simpleConversionMethodName) {
        this.simpleConversionMethodName = simpleConversionMethodName;
    }

    public DataTypeEnum getDataTypeEnum() {
        return dataTypeEnum;
    }

    public void setDataTypeEnum(DataTypeEnum dataTypeEnum) {
        this.dataTypeEnum = dataTypeEnum;
    }

    public String getNumFormat() {
        return numFormat;
    }

    public void setNumFormat(String numFormat) {
        this.numFormat = numFormat;
    }

    public Boolean getColumnHidden() {
        return columnHidden;
    }

    public void setColumnHidden(Boolean columnHidden) {
        this.columnHidden = columnHidden;
    }

    public Boolean getEmphasize() {
        return isEmphasize;
    }

    public void setEmphasize(Boolean emphasize) {
        isEmphasize = emphasize;
    }

    public String getEmphasizeColour() {
        return emphasizeColour;
    }

    public void setEmphasizeColour(String emphasizeColour) {
        this.emphasizeColour = emphasizeColour;
    }

    public String getEmphasizeMethodName() {
        return emphasizeMethodName;
    }

    public void setEmphasizeMethodName(String emphasizeMethodName) {
        this.emphasizeMethodName = emphasizeMethodName;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Integer getColumnHigh() {
        return columnHigh;
    }

    public void setColumnHigh(Integer columnHigh) {
        this.columnHigh = columnHigh;
    }

    public Boolean getFrame() {
        return frame;
    }

    public void setFrame(Boolean frame) {
        this.frame = frame;
    }
}
