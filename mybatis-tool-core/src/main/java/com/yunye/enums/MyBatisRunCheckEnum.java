package com.yunye.enums;

import com.yunye.common.enums.IEnum;

/**
 * mybatis数据规则校验
 * @author huangfu
 */
public enum MyBatisRunCheckEnum implements IEnum {
    CONDITION_IS_NULL("100000","查询条件不能为Null"),
    ;

    MyBatisRunCheckEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
