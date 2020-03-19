package com.yunye.common.utils;

import com.yunye.common.enums.IEnum;
import com.yunye.common.exception.MyBatisToolException;
import org.apache.commons.lang3.StringUtils;

/**
 * 规则校验
 * @author huangfu
 */
public class RuleCheckUtil {
    /**
     * 字符串是否为空
     * @param value 校验的值
     * @param iEnum 错误信息
     */
    public static void stringNotBlank(String value, IEnum iEnum){
        //规则校验
        if (StringUtils.isBlank(value)) {
            throw new MyBatisToolException(iEnum);
        }
    }

    /**
     * 对象数据校验
     * @param value 对象数据
     * @param iEnum 错误信息
     */
    public static void objectNotNull(Object value,IEnum iEnum){
        if(value == null){
            throw new MyBatisToolException(iEnum);
        }
    }


}
