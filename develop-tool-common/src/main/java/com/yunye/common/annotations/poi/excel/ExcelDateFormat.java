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
     * 导入时机
     * @return 返回导入时机的枚举
     */
    DateOpportunityEnum dateOpportunityEnum() default DateOpportunityEnum.ALL;

    /**
     * 时间格式
     * @return
     */
    String dateFormat() default "yyyy-MM-dd";
}
