package com.yunye.dao;

import com.yunye.help.SqlGenerateHelp;

import java.util.Map;

/**
 * 持久层基础操作 基类
 * @author huangfu
 */
public interface BaseDao {
    /**
     * 查询单条数据
     * @param sqlGenerateHelp
     * @return
     */
    Map<String,Object> findOnBySqlGenerateHelp(SqlGenerateHelp sqlGenerateHelp);
}
