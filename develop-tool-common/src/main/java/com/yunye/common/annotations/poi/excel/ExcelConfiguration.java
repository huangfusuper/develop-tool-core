package com.yunye.common.annotations.poi.excel;

import com.yunye.common.enums.poi.AlignmentEnum;

import java.lang.annotation.*;

/**
 * Excel的配置信息
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelConfiguration {
    /**
     * 文件名
     * @return 返回文件名
     */
    String fileName();

    /**
     * 主题名称
     * @return 主题名称
     */
    String titleName() default "";

    /**
     * 对齐方式
     * @return 对齐方式 仅限
     */
    AlignmentEnum alignment() default AlignmentEnum.CENTER_JUSTIFY;
}
