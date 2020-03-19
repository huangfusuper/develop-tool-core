package com.yunye.common.exception;

import com.yunye.common.enums.IEnum;

/**
 * 全局异常基类
 * @author huangfu
 */
public interface IException {
    /**
     * 返回运行枚举
     * @return 返回错误枚举
     */
    IEnum getIEnum();
}
