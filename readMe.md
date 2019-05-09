# 3.3 引入内嵌容器
优势：
1. 减少外部容器依赖，可移植性高
2. 方便测试部署
3. Springboot提供了可以插拔的内嵌容器方案

## 引入tomcat
直接引入 spring-boot-starter-web ,默认tomcat
## 引入jetty
1. 添加spring-boot-starter-jetty ,移除starter-tomcat
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```

## 引入filer拦截器
1. 新建 原始filter
```java
package com.buxz.imooc.houses.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
/*
* 请求过程中，打印日志
* */
public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("request -- coming " +servletRequest.getRemoteAddr());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
```
2. 包装成 spring bean
```java
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
```