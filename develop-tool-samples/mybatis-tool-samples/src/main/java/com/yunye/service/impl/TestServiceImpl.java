package com.yunye.service.impl;

import com.yunye.dao.TestDao;
import com.yunye.help.SqlGenerateHelp;
import com.yunye.help.criteria.Criteria;
import com.yunye.pojo.Test;
import com.yunye.service.BaseService;
import com.yunye.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author huangfu
 */
@Service
public class TestServiceImpl extends BaseService<TestDao> implements TestService {

    public TestServiceImpl(TestDao dao) {
        super(dao);
    }

    @Override
    public Test findOnById(String id) {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(Test.class);
        Criteria criteria = sqlGenerateHelp.createCriteria();
        criteria.andEqualTo("id",id);
        Test one = super.findOne(sqlGenerateHelp, Test.class);
        return one;
    }
}
