package com.yunye.service.impl;

import com.yunye.dao.TestDao;
import com.yunye.help.SqlGenerateHelp;
import com.yunye.help.criteria.Criteria;
import com.yunye.pojo.Test;
import com.yunye.service.BaseService;
import com.yunye.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return super.findOne(sqlGenerateHelp, Test.class);
    }

    @Override
    public List<Test> findList() {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(Test.class);
        sqlGenerateHelp.setGroupByClause("sex");
        sqlGenerateHelp.setTableColumns("sex");

        return super.findList(sqlGenerateHelp,Test.class);
    }

    @Override
    public Test save(Test test) {
        return super.save(test);
    }

    @Override
    public void update(Test test) {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(test);
        Criteria criteria = sqlGenerateHelp.createCriteria();
        criteria.andEqualTo("id",test.getId());
        super.updateByIdSelect(sqlGenerateHelp);
    }
}
