package com.yunye.common.annotations.poi.excel;

import java.lang.annotation.*;

/**
 * excel的属性字段映射
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {
    /**
     * 是否需要 边框
     * @return 返回是否需要边框 默认存在
     */
    boolean frame() default true;
}
