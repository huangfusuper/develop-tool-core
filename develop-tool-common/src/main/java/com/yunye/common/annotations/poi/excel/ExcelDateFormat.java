package com.yunye.common.annotations.poi.excel;

import com.yunye.common.enums.poi.DateOpportunityEnum;

import java.lang.annotation.*;

/**
 * Excel对时间的操作
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelDateFormat {
    /**
     * 时间格式
     * @return 时间格式
     */
    String value() default "yyyy-MM-dd";
}
