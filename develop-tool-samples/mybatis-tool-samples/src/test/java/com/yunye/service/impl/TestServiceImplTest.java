package com.yunye.service.impl;


import com.yunye.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestServiceImplTest {
    @Autowired
    private TestService testService;


    @Test
    public void findOnById(){
        testService.findOnById("11");
    }

}