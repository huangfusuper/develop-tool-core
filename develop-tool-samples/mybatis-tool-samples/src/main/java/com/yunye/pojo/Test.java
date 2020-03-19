package com.yunye.pojo;

import com.yunye.annotations.TableFieldName;
import com.yunye.annotations.TableName;
import lombok.Data;

/**
 * @author huangfu
 */
@Data
@TableName("test")
public class Test {
    private String id;
    private String name;
    private String sex;
    private String age;
    private String clazz;
}
