package com.yunye.common.annotations.mybatsis;

import java.lang.annotation.*;

/**
 * 映射表字段
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableFieldName {
    String value();
}
