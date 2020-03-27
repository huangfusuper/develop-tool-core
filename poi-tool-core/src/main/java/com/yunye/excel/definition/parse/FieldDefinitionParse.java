package com.yunye.excel.definition.parse;

import com.yunye.excel.definition.DefaultImportExcelEntityDefinition;

import java.util.Map;

/**
 * @author 属性解析
 */
public interface FieldDefinitionParse {
    /**
     * 解析excel实体对象
     * @return return
     */
    Map<String, DefaultImportExcelEntityDefinition> parsingImportExcelEntity();
}
