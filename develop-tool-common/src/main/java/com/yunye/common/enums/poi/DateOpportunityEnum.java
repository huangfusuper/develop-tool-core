package com.yunye.common.enums.poi;

/**
 * Date的导入导出时机
 * @author huangfu
 */
public enum  DateOpportunityEnum {
    ALL("ALL","导入导出都会被触发"),
    IMPORT("IMPORT","导入时生效，将时间格式化成标准字符串"),
    EXPORT("EXPORT","导出时生效，将时间转换成标准的字符串")
    ;
    private String status;
    private String details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    DateOpportunityEnum(String status, String details) {
        this.status = status;
        this.details = details;
    }
}
