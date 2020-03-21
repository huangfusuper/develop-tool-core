package com.yunye.help;

import com.yunye.common.annotations.mybatsis.JdbcType;
import com.yunye.common.annotations.mybatsis.TableFieldName;
import com.yunye.common.annotations.mybatsis.TableName;
import com.yunye.common.enums.mybatis.JdbcTypeEnum;
import com.yunye.common.utils.DevelopStringUtils;
import com.yunye.common.utils.ReflectUtils;
import com.yunye.conf.JavaJdbcType;
import com.yunye.dto.Page;
import com.yunye.help.criteria.Criteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL生成帮助类
 * @author huangfu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SqlGenerateHelp {
    /**
     * 是否去重
     */
    private boolean distinct;
    /**
     * 表列名
     */
    private String tableColumns;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 分页数据
     */
    private Page page;
    /**
     * 分组
     */
    protected String groupByClause;
    /**
     * 排序
     */
    protected String orderByClause;
    /**
     * 存放字段与结果的映射
     */
    private List<JavaJdbcType> tableFieldNameVales = new ArrayList<>(8);

    /**
     * 存放一组一组的条件 or分割
     */
    private List<Criteria> criteriaList = new ArrayList<>(2);

    public <T> SqlGenerateHelp(Class<T> entityClass) {
        this.parseEntity(entityClass);
    }

    /**
     * 修改或者保存是才会调用这个
     * @param entity 数据载体
     * @param <T> T
     */
    public <T> SqlGenerateHelp(T entity){
        this.parseEntityForUpdate(entity);
    }

    /**
     * 创建 添加 并返回条件对象
     * @return 条件对象
     */
   public Criteria createCriteria(){
       Criteria criteria = createCriteriaInternal();
       if(criteriaList.size() == 0){
           criteriaList.add(criteria);
       }
       return criteria;
   }

    /**
     * 创建一个条件对象
     * @return 条件对象
     */
    private  Criteria createCriteriaInternal() {
        return new Criteria();
    }
    /**
     * 解析实体，将类里面的字段映射为数据库字段
     * @param entityClass 实体类
     * @param <T> 实体类
     */
    private <T> void parseEntity(Class<T> entityClass){
        //Class<?> entityClass = entity.getClass()
        parseEntityTableName(entityClass);
        Field[] selfFields = entityClass.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : selfFields) {
            TableFieldName annotation = field.getAnnotation(TableFieldName.class);
            if(null != annotation){
                sb.append(annotation.value());
            }else{
                sb.append(DevelopStringUtils.humpToUnderline(field.getName()));
            }
            sb.append(",");
        }
        this.tableColumns = sb.substring(0,sb.length()-1);
    }

    /**
     * 解析表名称
     * @param entityClass 实体类的对象
     */
    private void parseEntityTableName(Class<?> entityClass){
        if (entityClass.isAnnotationPresent(TableName.class)) {
            TableName tableName = entityClass.getAnnotation(TableName.class);
            this.tableName = tableName.value();
        }else{
            String entityName = entityClass.getSimpleName();
            this.tableName = DevelopStringUtils.humpToUnderline(entityName);
        }
    }

    /**
     * 修改或插入时 ，自动拼装结果集
     * @param entity 保存的实体
     * @param <T> 实体
     */
    private <T> void parseEntityForUpdate(T entity){
        parseEntityTableName(entity.getClass());
        Field[] selfFields = entity.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : selfFields) {
            Object fieldValue = ReflectUtils.getFieldValue(entity, field);
            if(fieldValue != null){
                String jdbcType;
                JdbcType annotation = field.getAnnotation(JdbcType.class);
                if(annotation == null){
                    jdbcType = JdbcTypeEnum.VARCHAR.name();
                }else{
                    jdbcType = annotation.value().name();
                }

                String tableFieldName = DevelopStringUtils.humpToUnderline(field.getName());
                sb.append(tableFieldName).append(",");
                tableFieldNameVales.add(new JavaJdbcType(tableFieldName,fieldValue,jdbcType));
            }
        }
        this.tableColumns = sb.substring(0,sb.length()-1);
    }

    public static void main(String[] args) {
        System.out.println(JdbcTypeEnum.VARCHAR.name());
    }

}
