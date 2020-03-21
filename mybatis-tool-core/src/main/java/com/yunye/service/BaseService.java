package com.yunye.service;

import com.yunye.common.enums.mybatis.MyBatisRunCheckEnum;
import com.yunye.common.exception.MyBatisToolException;
import com.yunye.common.utils.ReflectUtils;
import com.yunye.dao.BaseDao;
import com.yunye.help.SqlGenerateHelp;
import com.yunye.help.criteria.Criteria;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 业务操作基类
 * @author huangfu
 */
public abstract class BaseService<D extends BaseDao> {
    private final D dao;

    public BaseService(D dao) {
        this.dao = dao;
    }

    /**
     * 查询单个数据
     * @param sqlGenerateHelp 查询条件构建
     * @param entityClass 返回的实体类型
     * @param <T> 实体类型
     * @return 返回的实体
     */
    protected <T> T baseFindOne(SqlGenerateHelp sqlGenerateHelp, Class<T> entityClass){
        Map<String, Object> onBySqlGenerateHelp = dao.findOnBySqlGenerateHelp(sqlGenerateHelp);
        return ReflectUtils.mapToBean(onBySqlGenerateHelp,entityClass);
    }

    /**
     * 根据条件查询集合
     * @param sqlGenerateHelp 查询条件
     * @param entityClass 类型对象
     * @param <T> 结果类型
     * @return 结果集
     */
    protected <T> List<T> baseFindList(SqlGenerateHelp sqlGenerateHelp, Class<T> entityClass){
        List<Map<String, Object>> findListMap = dao.findAllBySqlGenerateHelp(sqlGenerateHelp);
        return findListMap.stream()
                .map(entityMap -> ReflectUtils.mapToBean(entityMap, entityClass))
                .collect(Collectors.toList());
    }

    /**
     * 保存一个数据
     * @param entity 数据实体
     * @param <T> 载体类型
     * @return 数据载体
     */
    protected <T> T baseSave(T entity){
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(entity);
        this.saveAndUpdate(sqlGenerateHelp,false);
        return entity;
    }

    /**
     * 动态修改
     * @param sqlGenerateHelp 条件
     */
    protected void baseUpdateByIdSelect(SqlGenerateHelp sqlGenerateHelp){
        this.saveAndUpdate(sqlGenerateHelp,true);
    }

    /**
     * 根据条件删除  如果条件为空 则抛出异常避免删除全表的失误
     * @param sqlGenerateHelp 条件
     * @return 删除的数量
     */
    protected int baseDeleteByCondition(SqlGenerateHelp sqlGenerateHelp){
        List<Criteria> criteriaList = sqlGenerateHelp.getCriteriaList();
        if(criteriaList == null || criteriaList.size() ==0){
            throw new MyBatisToolException(MyBatisRunCheckEnum.CONDITION_NOT_IS_NULL);
        }

        if(StringUtils.isBlank(sqlGenerateHelp.getTableName())){
            throw new MyBatisToolException(MyBatisRunCheckEnum.TABLE_NAME_NOT_IS_NULL);
        }
        return dao.deleteBySqlGenerateHelp(sqlGenerateHelp);
    }

    /**
     * 删除表中全部数据
     * @param tableName 表名
     * @return 删除的条目
     */
    protected int baseDeleteAll(String tableName){
        return dao.deleteAll(tableName);
    }

    /**
     * 保存或者修改数据
     * @param sqlGenerateHelp 条件
     * @param isSave 是否是保存操作
     */
    private void saveAndUpdate(SqlGenerateHelp sqlGenerateHelp, boolean isSave){
        if(isSave){
            dao.updateSelectById(sqlGenerateHelp);
        }else{
            dao.saveEntity(sqlGenerateHelp);
        }
    }
}
