package com.yunye.service;

import com.yunye.dto.Page;
import com.yunye.pojo.MyBatisTestModel;

import java.util.List;

/**
 * @author huangfu
 */
public interface MyBatisTestModelService {
    /**
     * 根据id查询数据
     * @param id id
     * @return 实体
     */
    MyBatisTestModel findOnById(String id);

    /**
     * 查询全部
     * @return 结果集
     */
    Page<MyBatisTestModel> findList();

    /**
     * 保存操作
     * @param test
     * @return
     */
    MyBatisTestModel save(MyBatisTestModel test);

    /**
     *
     * @param test
     */
    void update(MyBatisTestModel test);

    /**
     * 根据id删除
     * @param id
     * @return re
     */
    int deleteById(String id);

    /**
     * 清空表数据
     * @param tableName
     * @return re
     */
    int deleteAll(String tableName);

    /**
     * 分组排序
     * @return
     */
    List<MyBatisTestModel> findAllGroupBySex();
}
