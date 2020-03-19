package com.yunye.common.exception;

import com.yunye.common.enums.IEnum;

/**
 * 异常数据实体
 * @author huangfu
 */
public class ExceptionModel implements IEnum {
    private String code;
    private String msg;

    public ExceptionModel(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ExceptionModel (IEnum iEnum){
        this.code = iEnum.getCode();
        this.msg = iEnum.getMsg();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
