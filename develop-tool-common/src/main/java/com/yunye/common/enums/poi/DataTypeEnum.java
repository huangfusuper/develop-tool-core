package com.yunye.common.enums.poi;

/**
 * 导出的数据类型
 * @author huangfu
 */
public enum DataTypeEnum {
    TEXT("TEXT","文本类型"),
    IMAGE("IMAGE","图片类型"),
    FUNCTION("FUNCTION","函数类型"),
    NUMBER("NUMBER","数字类型"),
    DATE("DATE","时间类型")

    ;
    private String dataType;
    private String details;

    DataTypeEnum(String dataType, String details) {
        this.dataType = dataType;
        this.details = details;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
