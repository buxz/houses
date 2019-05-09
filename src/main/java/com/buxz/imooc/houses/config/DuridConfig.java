package com.buxz.imooc.houses.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DuridConfig {

    /**
     * ConfigurationProperties 加载 application.properties配置文件中的以spring.druid开头的参数
     * @return
     */
    @ConfigurationProperties(prefix = "spring.druid")
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter())); // 连接池添加慢日志功能
        return dataSource;
    }

    /*
    * 慢 sql
    * */
    @Bean
    public Filter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(1); // 执行超过5秒的为慢sql
        statFilter.setLogSlowSql(true); // 打印日志
        statFilter.setMergeSql(true); // 整合sql
        return statFilter;
    }

    /**
     * 添加监控
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }


}
