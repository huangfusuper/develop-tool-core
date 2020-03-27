package com.yunye.excel.definition.parse;

import com.yunye.excel.definition.DefaultImportExcelEntityDefinition;
import com.yunye.excel.definition.base.BaseImportExcelEntityDefinition;

import java.util.Map;

/**
 * @author huangfu
 */
public abstract class BaseFieldDefinitionParse implements FieldDefinitionParse {
    @Override
    public Map<String, BaseImportExcelEntityDefinition> parsingImportExcelEntity() {
        return null;
    }
}
