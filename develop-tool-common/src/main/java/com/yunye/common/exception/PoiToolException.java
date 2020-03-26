package com.yunye.common.exception;

import com.yunye.common.enums.IEnum;

/**
 * Poi通用异常
 * @author huangfu
 */
public class PoiToolException extends RuntimeException implements IException {
    private IEnum iEnum;

    public PoiToolException(IEnum iEnum) {
        super(iEnum.getMsg());
        this.iEnum = iEnum;
    }

    public PoiToolException(Throwable cause) {
        super(cause);
        this.iEnum = new ExceptionModel("30000002",cause.getMessage());
    }

    @Override
    public IEnum getIEnum() {
        return iEnum;
    }
}
