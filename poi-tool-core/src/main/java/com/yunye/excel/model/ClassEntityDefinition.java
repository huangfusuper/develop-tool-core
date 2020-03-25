package com.yunye.excel.model;

import com.yunye.excel.definition.base.BaseExcelEntityDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<BaseExcelEntityDefinition> baseExcelEntityDefinitions;
}
