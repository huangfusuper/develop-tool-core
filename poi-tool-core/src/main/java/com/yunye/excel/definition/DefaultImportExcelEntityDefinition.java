package com.yunye.excel.definition;

import com.yunye.common.enums.poi.DataTypeEnum;
import com.yunye.excel.definition.base.BaseImportExcelEntityDefinition;
import lombok.*;

import java.util.Map;

/**
 * 默认的导入解析器
 * @author huangfu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DefaultImportExcelEntityDefinition extends BaseImportExcelEntityDefinition {

    /**
     * 列索引
     */
    private Integer columnIndex;
    /**
     * 时间类型
     */
    private String dateFormat = "yyyy-MM-dd";
    /**
     * 是否自动格式化内部数据
     */
    private Boolean trimStr;
    /**
     * 内部需要替换的数据
     */
    private Map<String,String> valueMap;
    /**
     * 简单的数据转换
     */
    private String simpleConversionMethodName;
    /**
     * 导入的数据类型
     */
    private DataTypeEnum dataTypeEnum;
    /**
     * 数字格式化的格式
     */
    private String numFormat;
}
