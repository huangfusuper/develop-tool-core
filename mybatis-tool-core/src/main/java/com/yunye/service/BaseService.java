package com.yunye.service;

import com.yunye.common.utils.ReflectUtils;
import com.yunye.dao.BaseDao;
import com.yunye.help.SqlGenerateHelp;

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
    public <T> T findOne(SqlGenerateHelp sqlGenerateHelp,Class<T> entityClass){
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
    public <T> List<T> findList(SqlGenerateHelp sqlGenerateHelp,Class<T> entityClass){
        List<Map<String, Object>> findListMap = dao.findAllBySqlGenerateHelp(sqlGenerateHelp);
        return findListMap.stream()
                .map(entityMap -> ReflectUtils.mapToBean(entityMap, entityClass))
                .collect(Collectors.toList());
    }
}
