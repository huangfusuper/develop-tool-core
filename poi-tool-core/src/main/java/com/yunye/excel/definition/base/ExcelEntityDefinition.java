package com.yunye.excel.definition.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Excel 实体定义基类
 * @author huangfu
 */
public interface ExcelEntityDefinition {

    /**
     * 解析excel实体对象
     * @param entityClass 实体的类对象
     */
    void parsingExcelEntity(Class entityClass);
}
