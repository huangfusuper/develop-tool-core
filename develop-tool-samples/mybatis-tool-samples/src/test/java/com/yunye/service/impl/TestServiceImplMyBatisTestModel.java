package com.yunye.service.impl;


import com.yunye.pojo.MyBatisTestModel;
import com.yunye.service.MyBatisTestModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestServiceImplMyBatisTestModel {
    @Autowired
    private MyBatisTestModelService testService;


    @Test
    public void findOnById(){
        MyBatisTestModel onById = testService.findOnById("100");
        System.out.println(onById);
    }

    @Test
    public void findAll(){
        List<MyBatisTestModel> list = testService.findList();
        list.forEach(e ->{
            System.out.println(e);
        });
    }

    @Test
    public void saveTest(){
        MyBatisTestModel build = MyBatisTestModel.builder()
                .id("100")
                .age(20L)
                .clazz("软件工程")
                .date(new Date())
                .sex("男")
                .userName("5656")
                .build();
        System.out.println(testService.save(build));
    }

    @Test
    public void findList(){
        MyBatisTestModel build = MyBatisTestModel.builder()
                .id("100")
                .clazz("电子信息系")
                .build();
        testService.update(build);
    }

    @Test
    public void deleteById(){
        System.out.println(testService.deleteById("100"));
    }

    @Test
    public void deleteAll(){
        System.out.println(testService.deleteAll("test"));
    }

    @Test
    public void findGroup(){
        System.out.println(testService.findAllGroupBySex());
    }

}