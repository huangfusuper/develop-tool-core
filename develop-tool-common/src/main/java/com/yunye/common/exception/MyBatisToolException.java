package com.yunye.common.exception;

import com.yunye.common.enums.IEnum;

/**
 * mybatis通用异常
 * @author huangfu
 */
public class MyBatisToolException extends RuntimeException implements IException {
    private IEnum iEnum;

    public MyBatisToolException(IEnum iEnum) {
        super(iEnum.getMsg());
        this.iEnum = iEnum;
    }

    @Override
    public IEnum getIEnum() {
        return this.iEnum;
    }
}
