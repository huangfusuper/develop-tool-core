package com.yunye.pojo;

import com.yunye.common.annotations.mybatsis.JdbcType;
import com.yunye.common.annotations.mybatsis.TableName;
import com.yunye.common.enums.mybatis.JdbcTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huangfu
 */
@Data
@TableName("test")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyBatisTestModel {
    private String id;
    private String userName;
    private String sex;
    @JdbcType(JdbcTypeEnum.BIGINT)
    private Long age;
    private String clazz;
    @JdbcType(JdbcTypeEnum.TIMESTAMP)
    private Date date;
}
