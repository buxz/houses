# springboot 整合 freemaker
## 1.引入依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
```
## 2.配置 application.properties
```properties
#freemarker 配置
spring.freemarker.charset=UTF-8
spring.freemarker.content-type=text/html;charset=UTF-8
spring.freemarker.expose-request-attributes=true
spring.freemarker.expose-session-attributes=true
spring.freemarker.expose-spring-macro-helpers=true
spring.freemarker.suffix=.ftl

spring.freemarker.settings.datetime_format=yyyy-MM-dd HH:mm:ss
spring.freemarker.settings.default_encoding=utf-8
```

## 3. 页面测试 
1. templates目录下 编写hello.ftl文件
```xml
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
hello.${user.name}
</body>
</html>
```
2. 编写controller测试页面 
```java
package com.buxz.imooc.houses.controller;

import com.buxz.imooc.houses.common.model.User;
import com.buxz.imooc.houses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(ModelMap modelMap){
        List<User> users = userService.getUsers();
        User user = users.get(0);
        modelMap.put("user",user);
        return "hello";
    }
}

```