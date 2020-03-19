package com.yunye.help.criteria;

import com.yunye.common.exception.ExceptionModel;
import com.yunye.common.utils.RuleCheckUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件拼接标准
 * @author huangfu
 */
@Data
@ToString
public class Criteria {
    /**
     * 条件的集合
     */
    private List<Criterion> criteria;

    public Criteria() {
        super();
        this.criteria = new ArrayList<>(8);
    }

    /**
     * 值为空判断
     * @param fileName 字段名称
     * @return 对象本身
     */
    public Criteria andIsNull(String fileName){
        addCriterion(fileName + " is null",fileName);
        return this;
    }

    /**
     * 值非空判断
     * @param fileName 字段名称
     * @return 对象本身
     */
    public Criteria andIsNotNull(String fileName){
        addCriterion(fileName + " is not null",fileName);
        return this;
    }

    /**
     * 等于 条件添加
     * @param fileName 字段名称
     * @param value 数据
     * @return 对象本身
     */
    public Criteria andEqualTo(String fileName,Object value){
        addCriterion(fileName + " = ", value, fileName);
        return this;
    }

    /**
     * 不等于  条件添加
     * @param fileName 字段名字
     * @param value 数据
     * @return 对象本身
     */
    public Criteria andNotEqualTo(String fileName,Object value){
        addCriterion(fileName + " <> ", value, fileName);
        return this;
    }

    /**
     * 大于  条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andGreaterThan(String fileName,Object value){
        addCriterion(fileName + " > ", value, fileName);
        return this;
    }

    /**
     * 大于等于  条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andGreaterThanOrEqualTo(String fileName,Object value){
        addCriterion(fileName + " >= ", value, fileName);
        return this;
    }

    /**
     * 小于  条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andLessThan(String fileName,Object value){
        addCriterion(fileName + " < ", value, fileName);
        return this;
    }

    /**
     * 小于等于  条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andLessThanOrEqualTo(String fileName,Object value){
        addCriterion(fileName + " <= ", value, fileName);
        return this;
    }

    /**
     * like 条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andLike(String fileName,Object value){
        addCriterion(fileName + " like ",value,fileName);
        return this;
    }

    /**
     * like 条件添加 前置匹配
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andBeforeLike(String fileName,Object value){
        addCriterion(fileName + " like ","%"+value,fileName);
        return this;
    }
    /**
     * like 条件添加  后置匹配
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andAfterLike(String fileName,Object value){
        addCriterion(fileName + " like ",value+"%",fileName);
        return this;
    }

    /**
     * not like 条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andNotLike(String fileName,Object value){
        addCriterion(fileName + " not like ",value,fileName);
        return this;
    }

    /**
     * in 条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andIn(String fileName,List<Object> value){
        addCriterion(fileName + " in ",value,fileName);
        return this;
    }

    /**
     * not in 条件添加
     * @param fileName 字段名称
     * @param value 字段值
     * @return 对象本身
     */
    public Criteria andNotIn(String fileName,List<Object> value){
        addCriterion(fileName + " not in ",value,fileName);
        return this;
    }

    /**
     * between 条件添加
     * @param fileName 字段名称
     * @param value1 字段值
     * @param value2 字段值2
     * @return 对象本身
     */
    public Criteria andBetween(String fileName,Object value1,Object value2){
        addCriterion(fileName + " between ",value1,value2,fileName);
        return this;
    }

    /**
     * not between 条件添加
     * @param fileName 字段名称
     * @param value1 字段值
     * @param value2 字段值2
     * @return 对象本身
     */
    public Criteria andNotBetween(String fileName,Object value1,Object value2){
        addCriterion(fileName + " not between ",value1,value2,fileName);
        return this;
    }


    /**
     * 返回是否有条件
     * @return 是否存在条件
     */
    public boolean isValid(){
        return criteria.size() > 0;
    }

    /**
     * 添加一个条件
     * 作用：这个方式是添加一个无值条件  类似   name isNull 之类的数据
     * @param condition 条件类似  name isNull
     */
    private void addCriterion(String condition, String property){
        RuleCheckUtil.stringNotBlank(condition, new ExceptionModel("100001","condition for"+property+"is Blank"));
        criteria.add(new Criterion(condition));
    }

    /**
     * 添加一个条件
     * 作用：这个是添加一个比较条件  类似  age > 15 或者 age in(1,2,3)之类的数据
     * @param condition 条件 age > 15 或者 age in(1,2,3)
     * @param value 数值  当数据为list时是in匹配
     */
    private void addCriterion(String condition,Object value, String property){
        RuleCheckUtil.stringNotBlank(condition, new ExceptionModel("100001","condition for"+property+"is Blank"));
        RuleCheckUtil.objectNotNull(value,new ExceptionModel("100002","Value for"+property+"is null"));
        RuleCheckUtil.stringNotBlank(value.toString(), new ExceptionModel("100003","Value for"+property+"is Blank"));
        criteria.add(new Criterion(condition,value));
    }

    /**
     * 添加一个条件
     * 作用：这是一个区间添加器 类似 between 1 and 5  之类的数据
     * @param condition 条件
     * @param value 起始值
     * @param value2 末值
     * @param property 字段
     */
    private void addCriterion(String condition,Object value,Object value2,String property){
        RuleCheckUtil.objectNotNull(value,new ExceptionModel("100002","Value for"+property+"is null"));
        RuleCheckUtil.objectNotNull(value2,new ExceptionModel("100002","Value for"+property+"is null"));
        RuleCheckUtil.stringNotBlank(value.toString(), new ExceptionModel("100003","Value for"+property+"is Blank"));
        RuleCheckUtil.stringNotBlank(value2.toString(), new ExceptionModel("100003","Value for"+property+"is Blank"));
        criteria.add(new Criterion(condition,value,value2));
    }
}
