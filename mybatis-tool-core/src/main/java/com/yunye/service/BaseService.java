package com.yunye.service;

import com.yunye.dao.BaseDao;
import com.yunye.help.SqlGenerateHelp;

import java.util.Map;

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
        Map<?, ?> onBySqlGenerateHelp = dao.findOnBySqlGenerateHelp(sqlGenerateHelp);
        return null;
    }
}
