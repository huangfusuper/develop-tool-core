package com.yunye.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * java和jdbc的对照表
 * @author huangfu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JavaJdbcType {
    private Object value;
    private String jdbcType;
}
