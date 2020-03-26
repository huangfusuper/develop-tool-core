package com.yunye.excel.definition.base;

import com.yunye.excel.model.ClassEntityDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Excel 实体定义基类
 * @author huangfu
 */
public interface ExcelEntityDefinition {

    /**
     * 解析excel实体对象
     * @return return
     */
    Map<String,ExcelEntityDefinition> parsingImportExcelEntity();
}
