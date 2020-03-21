package com.yunye.service.impl;

import com.yunye.dao.MyBatisTestModelDao;
import com.yunye.dto.Page;
import com.yunye.help.SqlGenerateHelp;
import com.yunye.help.criteria.Criteria;
import com.yunye.pojo.MyBatisTestModel;
import com.yunye.service.BaseService;
import com.yunye.service.MyBatisTestModelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangfu
 */
@Service
public class MyBatisTestModelServiceImpl extends BaseService<MyBatisTestModelDao> implements MyBatisTestModelService {

    public MyBatisTestModelServiceImpl(MyBatisTestModelDao dao) {
        super(dao);
    }

    @Override
    public MyBatisTestModel findOnById(String id) {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(MyBatisTestModel.class);
        Criteria criteria = sqlGenerateHelp.createCriteria();
        criteria.andEqualTo("id",id);
        return super.baseFindOne(sqlGenerateHelp, MyBatisTestModel.class);
    }

    @Override
    public Page<MyBatisTestModel> findList() {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(MyBatisTestModel.class);
        Page<MyBatisTestModel> page = new Page<>();
        page.setPageSize(2);
        sqlGenerateHelp.setPage(page);
        List<MyBatisTestModel> myBatisTestModels = super.baseFindList(sqlGenerateHelp, MyBatisTestModel.class);
        page.setResults(myBatisTestModels);
        return page;
    }

    @Override
    public MyBatisTestModel save(MyBatisTestModel test) {
        return super.baseSave(test);
    }

    @Override
    public void update(MyBatisTestModel test) {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(test);
        Criteria criteria = sqlGenerateHelp.createCriteria();
        criteria.andEqualTo("id",test.getId());
        super.baseUpdateByIdSelect(sqlGenerateHelp);
    }

    @Override
    public int deleteById(String id) {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(MyBatisTestModel.class);
        Criteria criteria = sqlGenerateHelp.createCriteria();
        criteria.andEqualTo("id",id);
        return super.baseDeleteByCondition(sqlGenerateHelp);
    }

    @Override
    public int deleteAll(String tableName) {
        return super.baseDeleteAll(tableName);
    }

    @Override
    public List<MyBatisTestModel> findAllGroupBySex() {
        SqlGenerateHelp sqlGenerateHelp = new SqlGenerateHelp(MyBatisTestModel.class);
        sqlGenerateHelp.setTableColumns("sex");
        sqlGenerateHelp.setGroupByClause("sex");
        return super.baseFindList(sqlGenerateHelp,MyBatisTestModel.class);
    }
}
