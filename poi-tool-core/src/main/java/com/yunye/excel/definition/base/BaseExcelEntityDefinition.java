package com.yunye.excel.definition.base;

import com.yunye.common.enums.poi.DataTypeEnum;
import com.yunye.common.enums.poi.DateOpportunityEnum;
import com.yunye.excel.Import.conf.ImportExcelProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析器
 * @author huangfu
 */
public abstract class BaseExcelEntityDefinition implements ExcelEntityDefinition {
    @Override
    public Map<String, ExcelEntityDefinition> parsingImportExcelEntity() {
        return null;
    }
}
