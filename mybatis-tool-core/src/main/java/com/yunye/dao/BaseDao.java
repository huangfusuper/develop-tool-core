package com.yunye.dao;

import com.yunye.help.SqlGenerateHelp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 持久层基础操作 基类
 * @author huangfu
 */
public interface BaseDao {
    /**
     * 查询单条数据
     * @param sqlGenerateHelp 查询条件
     * @return 数据结果
     */
    Map<String,Object> findOnBySqlGenerateHelp(SqlGenerateHelp sqlGenerateHelp);

    /**
     * 多条数据查询
     * @param sqlGenerateHelp 查询条件
     * @return 数据结果
     */
    List<Map<String,Object>> findAllBySqlGenerateHelp(SqlGenerateHelp sqlGenerateHelp);

    /**
     * 保存一个实体
     * @param sqlGenerateHelp 帮助器
     */
    void saveEntity(SqlGenerateHelp sqlGenerateHelp);

    /**
     * 根据主键修改数据 动态修改
     * @param sqlGenerateHelp 帮助器
     */
    void updateSelectById(SqlGenerateHelp sqlGenerateHelp);

    /**
     * 删除方法
     * @param sqlGenerateHelp 帮助器
     * @return 删除的数量
     */
    int deleteBySqlGenerateHelp(SqlGenerateHelp sqlGenerateHelp);

    /**
     * 表名称
     * @param tableName 表名
     * @return 删除的数量
     */
    int deleteAll(@Param("tableName") String tableName);
}
