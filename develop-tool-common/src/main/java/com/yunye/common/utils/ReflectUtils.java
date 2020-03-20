package com.yunye.common.utils;

import com.yunye.common.annotations.TableFieldName;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;

/**
 * 反射工具类
 * @author huangfu
 */
public class ReflectUtils {
    private static final String OBJECT_CLASS_NAME = "java.lang.object";
    private static final String INTEGER_CLASS_NAME="java.lang.Integer";
    private static final String DOUBLE_CLASS_NAME="java.lang.Double";
    private static final String STRING_CLASS_NAME="java.lang.String";
    private static final String LONG_CLASS_NAME="java.lang.Long";
    private static final String DATE_CLASS_NAME="java.util.Date";
    private static final String BIG_INTEGER_CLASS_NAME="java.math.BigInteger";

    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.S";

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

    /**
     * 将指定的map转换为bean
     * @param map 要转换的元数据
     * @param entityClass 要转换的实体
     * @param <T> 实体类型
     * @return 实体对象
     */
    public static <T> T mapToBean(Map<String,String> map,Class<T> entityClass){
        if(map == null){
            return null;
        }
        try {
            T entity = entityClass.newInstance();
            List<Field> allField = getAllField(entityClass);
            Map<String, String> beanMap = mapToBeanMap(map);

            allField.forEach(field -> {
                String fieldName = "";
                String typeName = field.getType().getName();
                TableFieldName tableFieldNameAnn = field.getAnnotation(TableFieldName.class);
                if(tableFieldNameAnn != null){
                    String tableFieldName = tableFieldNameAnn.value();
                    fieldName = DevelopStringUtils.underlineToHump(tableFieldName);
                }else{
                    fieldName = field.getName();
                }
                try {
                    Object value = valueFormatType(beanMap.get(fieldName), typeName);
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, entityClass);
                    Method method = pd.getWriteMethod();
                    method.invoke(entity,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            return entity;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 值类型格式化
     * @param value 值
     * @param typeName 类型名称
     * @return 格式化好的数据
     */
    public static Object valueFormatType(String value,String typeName){
        Object formatValue;
        switch (typeName){
            case INTEGER_CLASS_NAME:
                formatValue = Integer.parseInt(value);
                break;
            case DOUBLE_CLASS_NAME:
                formatValue = Double.parseDouble(value);
                break;
            case LONG_CLASS_NAME:
                formatValue = Long.parseLong(value);
                break;
            case DATE_CLASS_NAME:
                boolean dateIsTime = value.contains(".");
                if(dateIsTime){
                    formatValue = DateUtil.strToDateTime(value,YYYY_MM_DD_HH_MM_SS_S);
                }else{
                    formatValue = DateUtil.strToDate(value,YYYY_MM_DD);
                }
                break;
            case BIG_INTEGER_CLASS_NAME:
                formatValue = new BigInteger(value);
                break;
            default:
                formatValue = value;
                break;
        }
        return formatValue;
    }

    /**
     * 获得所有上层父类的属性（不包含object的属性）
     * @param clazz 类的对象
     * @return 返回对象的全部属性
     */
    public static List<Field> getAllField(Class<?> clazz){
        List<Field> fieldList = new ArrayList<>() ;
        Class<?> tempClass = clazz;
        while(tempClass != null && !tempClass.getName().toLowerCase().equals(OBJECT_CLASS_NAME)) {
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 将map转换为bean的标准map
     * @param map 数据map
     * @return 标注map
     */
    private static Map<String,String> mapToBeanMap(Map<String,String> map){
        Map<String,String> entityNameMap = new HashMap<>(8);
        map.forEach((key,value) ->{
            String beanFieldName = DevelopStringUtils.underlineToHump(key);
            entityNameMap.put(beanFieldName,value);
        });
        return entityNameMap;
    }
}