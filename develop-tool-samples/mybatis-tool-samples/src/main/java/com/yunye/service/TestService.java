package com.yunye.service;

import com.yunye.pojo.Test;

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
}
