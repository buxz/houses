# 引入依赖
```xml
   <!-- mybatis起步依赖-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.2.0</version>
        </dependency>
        <!-- mysql依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- 引入数据库连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.12</version>
        </dependency>
```
# 配置druid
## 1.application.properties
```properties
# 配置druid
spring.druid.url=jdbc:mysql://localhost:3306/houses?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false
spring.druid.username=root
spring.druid.password=6da76fa14f
spring.druid.driver-class-name=com.mysql.jdbc.Driver

# 最大连接数
spring.druid.maxActive=30
# 最小连接数(一直保持五个连接数)
spring.druid.minIdle=5
# 获取连接的最大等待时间(常见于高并发下连接等待)
spring.druid.maxWait=10000
# 解决mysql 8 小时问题（mysql 主动断开超过8小时空闲的链接）
spring.druid.validationQuery= select 'x'
# 空闲连接检查时间间隔
spring.druid.timeBetweenEvictionRunsMillis=60000
#空闲连接最小空闲时间（超过该时间及认为是空闲连接）
spring.druid.minEvictableIdleTimeMillis=300000

#mybatis配置
mybatis.config-location=classpath:mybatis/mybatis-config.xml
```
## 2.DruidConfig
```java
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
```

## 3.mybatis-config.xml(可以在application.properties直接配置)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <settings>
        <!-- 配置关闭缓存-->
        <setting name="cacheEnabled" value="false"/>

        <!-- 事务的超时时间-->
        <setting name="defaultStatementTimeout" value="600"/>

        <!-- 驼峰命名-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <setting name="useGeneratedKeys" value="true"/>

        <!-- 缓存 prepareStament 提高性能-->
        <setting name="defaultExecutorType" value="REUSE"/>
    </settings>

    <!-- Continue going here -->
    <!-- 配置别名-->
    <typeAliases>
        <typeAlias type="com.buxz.imooc.houses.common.model.User" alias="user"/>
    </typeAliases>

    <!-- 配置xml文件路径-->
    <mappers>
        <mapper resource="mapper/user.xml"/>
    </mappers>
</configuration>
```
