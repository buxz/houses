package com.buxz.imooc.houses.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterBeanConfig {

    /**
     * 创建filterBean 需要以下步骤
     * 1. 构造filter
     * 2. 配置 拦截的urlPattern
     * 3. 利用FilterRegistrationBean进行包装
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        List<String> urlList = new ArrayList<>();
        urlList.add("*"); // 拦截所有请求
        filterRegistrationBean.setUrlPatterns(urlList);
        return  filterRegistrationBean;
    }
}
