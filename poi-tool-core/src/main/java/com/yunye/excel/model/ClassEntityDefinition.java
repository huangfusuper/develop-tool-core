package com.yunye.excel.model;

import com.yunye.excel.definition.base.ExcelEntityDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类与内部字段的映射
 * @author huangfu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassEntityDefinition {
    private String className;
    private Map<String,ExcelEntityDefinition> importExcelEntityDefinition = new ConcurrentHashMap<>(8);
    private Map<String,ExcelEntityDefinition> exportExcelEntityDefinition = new ConcurrentHashMap<>(8);
}
