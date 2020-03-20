package com.yunye.service;

import com.yunye.pojo.Test;

import java.util.List;

/**
 * @author huangfu
 */
public interface TestService {
    /**
     * 根据id查询数据
     * @param id id
     * @return 实体
     */
    Test findOnById(String id);

    /**
     * 查询全部
     * @return 结果集
     */
    List<Test> findList();
}
