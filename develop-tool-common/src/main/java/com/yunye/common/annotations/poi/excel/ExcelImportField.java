package com.yunye.common.annotations.poi.excel;

import com.yunye.common.enums.poi.DataTypeEnum;

import java.lang.annotation.*;

/**
 * excel的属性字段映射
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelImportField {

    /**
     * 改属性对应数据的库的那一列
     * @return 返回列对应索引
     */
    int columnIndex();

    /**
     * 是否对数据进行格式化
     * @return 是否格式化
     */
    boolean trimStr() default false;

    /**
     * 替换数据
     * @return 返回替换的数据
     */
    String[] replace() default {};

    /**
     * 替换的方法名称
     * @return 替换的方法名称
     */
    String simpleConversionMethodName() default "";

    /**
     * 返回数据类型
     * @return 返回数据类型
     */
    DataTypeEnum dataTypeEnum() default DataTypeEnum.TEXT;

    /**
     * 数字格式化
     * @return 返回数字格式化的格式
     */
    String numFormat() default "#";
}
