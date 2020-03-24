package com.yunye.excel.definition.base;

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
    private Map<String,BaseExcelEntityDefinition> definePool = new ConcurrentHashMap<>(8);
    /**
     * 列索引
     */
    private Integer columnIndex;
    /**
     * 是否需要边框
     */
    private Boolean frame = false;
    /**
     * 事件类型
     */
    private String databaseFormat = "yyyy-MM-dd";
    /**
     * 是否基于相同值合并同列的数据
     */
    private Boolean mergeVertical = false;
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
     * 导出的数据类型 导出类型 1 是文本 2 是图片,3 是函数,10 是数字 默认是文本
     */
    private String exportType = "1";
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

    public Map<String, BaseExcelEntityDefinition> getDefinePool() {
        return definePool;
    }

    public void setDefinePool(Map<String, BaseExcelEntityDefinition> definePool) {
        this.definePool = definePool;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Boolean getFrame() {
        return frame;
    }

    public void setFrame(Boolean frame) {
        this.frame = frame;
    }

    public String getDatabaseFormat() {
        return databaseFormat;
    }

    public void setDatabaseFormat(String databaseFormat) {
        this.databaseFormat = databaseFormat;
    }

    public Boolean getMergeVertical() {
        return mergeVertical;
    }

    public void setMergeVertical(Boolean mergeVertical) {
        this.mergeVertical = mergeVertical;
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

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
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
}
