package com.yunye.excel.definition.base;

import com.yunye.common.enums.poi.DataTypeEnum;

import java.util.Map;

/**
 * 属性定义基类
 * @author huangfu
 */
 interface EntityDefinition {
    /**
     * 返回索引列
     * @return 返回索引列
     */
      Integer getColumnIndex();
    /**
     * 设置索引列
     * @param columnIndex 索引列
     */
      void setColumnIndex(Integer columnIndex);

    /**
     * 返回时间格式
     * @return 返回时间格式
     */
      String getDateFormat();

    /**
     * 设置时间格式
     * @param dateFormat 时间格式
     */
      void setDateFormat(String dateFormat);

    /**
     * 是否格式化数据
     * @return 是否格式化数据
     */
      Boolean getTrimStr();

    /**
     * 是否格式化数据
     * @param trimStr 是否
     */
      void setTrimStr(Boolean trimStr);

    /**
     * 返回映射数据
     * @return 映射数据
     */
      Map<String, String> getValueMap();

    /**
     * 设置映射值
     * @param valueMap 映射值
     */
      void setValueMap(Map<String, String> valueMap);

    /**
     * 简单的方法数据替换
     * @return 方法数据替换
     */
      String getSimpleConversionMethodName();

    /**
     * 设置映射值方法
     * @param simpleConversionMethodName 方法名称
     */
      void setSimpleConversionMethodName(String simpleConversionMethodName);

    /**
     * 数据类型
     * @return 数据类型
     */
      DataTypeEnum getDataTypeEnum();

    /**
     * 设置数据类型
     * @param dataTypeEnum 数据类型
     */
      void setDataTypeEnum(DataTypeEnum dataTypeEnum);

    /**
     * 需要将数据格式化成什么样？
     * @return 数据格式
     */
      String getNumFormat();

    /**
     * 设置数字格式化的格式
     * @param numFormat 设置数字格式化的格式
     */
      void setNumFormat(String numFormat);
}
