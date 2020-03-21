package com.yunye.common.annotations.mybatsis;

import com.yunye.common.enums.mybatis.JdbcTypeEnum;

import java.lang.annotation.*;

/**
 * 字段类型映射
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JdbcType {
    /**
     * 设定jdbc的类型
     * @return jdbc类型
     */
    JdbcTypeEnum value();
}
