package com.yunye.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据和类型的对照表
 * @author huangfu
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValueTypeDto {
    private Object value;
    private String javaType;

    public void setValue(Object value) {
        this.value = value;
    }
}
