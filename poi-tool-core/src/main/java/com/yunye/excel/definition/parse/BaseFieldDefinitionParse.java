package com.yunye.excel.definition.parse;

import com.yunye.excel.definition.DefaultImportExcelEntityDefinition;

import java.util.Map;

/**
 * @author huangfu
 */
public abstract class BaseFieldDefinitionParse implements FieldDefinitionParse {
    @Override
    public Map<String, DefaultImportExcelEntityDefinition> parsingImportExcelEntity() {
        return null;
    }
}
