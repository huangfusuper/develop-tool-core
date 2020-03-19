package com.yunye.help.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 条件标准
 * @author huangfu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Criterion {
    /**
     * 条件
     */
    private String condition;
    /**
     * 条件的值
     */
    private Object value;
    /**
     * 第二个值 这个主要运用于区间运算时第二个值
     */
    private Object secondValue;
    /**
     * 判断是否是没有值的运算条件 例如 name isNull  之类的条件  他是不存在条件值的
     */
    private boolean noValue;
    /**
     * 简单的值 就是扑通的 等于啦  比大小之类的普通值 判断
     */
    private boolean singleValue;
    /**
     * 是否是区间值运算
     */
    private boolean betweenValue;
    /**
     * 值是否是多个  例如in
     */
    private boolean listValue;

    /**
     * 这个是无值条件构造
     * @param condition 条件
     */
    Criterion(String condition) {
        super();
        this.condition = condition;
        this.noValue = true;
    }

    /**
     * 比较条件构造  age>12 或者  age in(1,2,3)
     * @param condition 条件
     * @param value 数据
     */
    Criterion(String condition,Object value){
        super();
        this.condition = condition;
        this.value = value;
        if(value instanceof List<?>){
            this.listValue = true;
        }else{
            this.singleValue = true;
        }
    }

    /**
     * 区间条件构造函数
     * @param condition 条件
     * @param value 起始值
     * @param value2 末值
     */
    Criterion(String condition,Object value,Object value2){
        super();
        this.condition = condition;
        this.betweenValue = true;
        this.value = value;
        this.secondValue = value2;
    }
}
