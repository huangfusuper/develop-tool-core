package com.yunye.excel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于统计操作错误的数据载体
 * @author huangfu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelOperaErrorModel<T> {
    public T entity;
    public String errorMsg;
}
