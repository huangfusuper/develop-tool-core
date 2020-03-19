package com.yunye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MyBatis 测试类
 * @author huangfu
 */
@SpringBootApplication
@MapperScan("com.yunye.dao")
public class MyBatisToolSamplesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBatisToolSamplesApplication.class,args);
    }
}
