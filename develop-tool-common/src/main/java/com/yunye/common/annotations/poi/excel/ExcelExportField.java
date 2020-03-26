package com.yunye.common.annotations.poi.excel;

import java.lang.annotation.*;

/**
 * 导出的配置
 * @author huangfu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelExportField {
    /**
     * 是否需要 边框
     * @return 返回是否需要边框 默认存在
     */
    boolean frame() default true;

    /**
     * 改属性对应数据的库的那一列
     * @return 返回列对应索引
     */
    int columnIndex();
}
