package com.yunye.service.impl;

import com.yunye.dao.TestDao;
import com.yunye.help.SqlGenerateHelp;
import com.yunye.help.criteria.Criteria;
import com.yunye.pojo.Test;
import com.yunye.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author huangfu
 */
@Service
public class TestServiceImpl implements TestService {

    private final TestDao testDao;

    public TestServiceImpl(TestDao testDao) {
        this.testDao = testDao;
    }

    @Override
    public Test findOnById(String id) {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(Test.class);
        Criteria criteria = sqlGenerateHelp.createCriteria();
        criteria.andEqualTo("id","11");
        System.out.println(criteria);
        System.out.println(testDao.findOnBySqlGenerateHelp(sqlGenerateHelp));
        return null;
    }
}
