package com.yunye.common.annotations.mybatsis;

import java.lang.annotation.*;

/**
 * 映射表名称
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableName {
    String value();
}
