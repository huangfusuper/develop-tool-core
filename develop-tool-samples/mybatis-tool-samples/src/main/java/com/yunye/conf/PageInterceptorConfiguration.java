package com.yunye.conf;

import com.yunye.interceptor.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置分页插件
 * @author huangfu
 */
@Configuration
public class PageInterceptorConfiguration {

    @Bean
    public Interceptor pageInterceptor(){
        return new PageInterceptor();
    }
}
