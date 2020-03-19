package com.yunye.common.utils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 * @author huangfu
 */
public class ReflectUtils {
    /**
     * 获取指定参数的值
     * @param obj 要获取的对象
     * @param field 要获取的参数对象
     * @param <T> 对象
     * @return 返回参数值
     */
    public static <T> Object getFieldValue(T obj, Field field) {
        Object result = null;
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
