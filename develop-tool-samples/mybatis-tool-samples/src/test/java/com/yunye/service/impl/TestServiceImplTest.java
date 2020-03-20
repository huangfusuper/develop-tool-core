package com.yunye.service.impl;


import com.yunye.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestServiceImplTest {
    @Autowired
    private TestService testService;


    @Test
    public void findOnById(){
        com.yunye.pojo.Test onById = testService.findOnById("11");
        System.out.println(onById);
    }

    @Test
    public void findAll(){
        List<com.yunye.pojo.Test> list = testService.findList();
        list.forEach(e ->{
            System.out.println(e);
        });
    }

}