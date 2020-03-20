package com.yunye.pojo;

import com.yunye.common.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author huangfu
 */
@Data
@TableName("test")
public class Test {
    private String id;
    private String userName;
    private String sex;
    private Long age;
    private String clazz;
    private Date date;
}
