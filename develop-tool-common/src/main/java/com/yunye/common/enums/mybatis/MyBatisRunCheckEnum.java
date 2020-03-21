package com.yunye.common.enums.mybatis;

import com.yunye.common.enums.IEnum;

/**
 * mybatis数据规则校验
 * @author huangfu
 */
public enum MyBatisRunCheckEnum implements IEnum {
    CONDITION_NOT_IS_NULL("200000","删除条件不能为空"),
    TABLE_NAME_NOT_IS_NULL("200001","表名不能为空#详细请参考：com.yunye.help.SqlGenerateHelp.SqlGenerateHelp(java.lang.Class<T>)"),
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
