#  Security6.1.0入门【1】

* 写这个笔记主要是因为，SpringSecurity6.1.0的教程太少了，我找教程的时候找了好长时间
* 最后只能去官网翻教程，去官方仓库找代码，去官方代码里看注释
* 然后发现SpringSecurity6.1.0更新了配置类的使用
* 主要是链式编程变成了函数加链式编程，跟以前相比，现在的更具有可读性
* 就好像以前是`http.csrf().disable()`，现在是`http.csrf(CsrfConfigurer::disable)`
* 不再是在对应配置后面进行链式配置，而是在对应配置的函数之中链式配置
* 如果降低版本，将函数中的提出来就好

## 入门小DEMO

> 主要是从配置文件入手

### 引入依赖

> 引入snakeyaml是为了消除SpringSecurity的警告
>
> 警告上说SpringSecurity的snakeyaml版本为1.33，这个有漏洞

* pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>mySecutiy</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>mySecutiy</name>
    <description>mySecutiy</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <!-- SpringSecurity的依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- SpringSecurity的snakeyaml版本为1.33，引入这个消除警告-->
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>2.0</version>
        </dependency>
        <!-- Spring基础开发-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

### SpringSecurity配置类

> 这里的登录相关接口迟早要删去，因为我后面不会使用它的接口，
>
> 而是在自己的接口中为用户授予权限
>
> 但作为一个入门Demo，已经足够

```java
/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] quiet = {"/", "/index.html", "/user/**"}; // 静态资源，以数组的形式抽离可变参数
        String[] rearEnd = {"/demo"}; // 后端接口
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(quiet).permitAll() // 放行的静态资源路径
                        .requestMatchers(rearEnd).permitAll() // 放行的后端接口路径
                        .anyRequest().authenticated() // 其他地址的访问均需验证权限
                )
                .formLogin((form) -> form
                        .loginPage("/power/noLogin") // 设置未登录的重定向路径
                        .usernameParameter("username") // 调试：登录时的用户名参数
                        .passwordParameter("password") // 调试：登录时的密码参数
                        .loginProcessingUrl("/cao/toLogin") // 调试：登录时的参数提交地址，不需要自己的接口
                        // 登录成功路径，但如果是从未登录的地方跳转来的，会跳回去，不建议使用
                        //  .successForwardUrl("/power/suc")
                        .defaultSuccessUrl("/power/suc",true) // 登录成功之后跳转的路径
                        .failureUrl("/power/err") // 登录失败的重定向路径
                        .permitAll() // 将这些路径放行，不然会重定向过多
                )
                // .formLogin(withDefaults()) // 为了方便入门，使用默认的登录页面
                .csrf(CsrfConfigurer::disable) // 关闭跨站攻击防护
                .rememberMe(withDefaults()) // 关于记住我，默认保存两周，登录时参数名的默认值为remember-me
                .logout((logout) -> logout
                        .logoutUrl("/cao/logout") // 退出登录的路径，不需要自己的接口
                        .logoutSuccessUrl("/power/logout-suc") // 退出登录成功的重定向路径
                        .permitAll() // 放行路径，理由同上
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // 下面是设置密码，这里主要是加密的示例，正常要从数据库读取
        UserDetails user =
                User.withUsername("qwe")
                        .password(new BCryptPasswordEncoder().encode("123"))
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * 密码器，开启加密
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### 配置类相关的控制器

> 为了前后端分离，不能使用重定向，
>
> 在后端使用返回值，让前端根据返回值进行页面跳转

```java
/**
 * 关于权限校验的接口
 */
@RestController
@RequestMapping("/power")
public class PowerController {
    private static final Logger log = LoggerFactory.getLogger(PowerController.class);

    /**
     * 未登录的重定向
     */
    @GetMapping("/noLogin")
    public Res noLogin() {
        return new Res(401, "当前未登录");
    }

    /**
     * 登录失败的路径
     */
    @GetMapping("/err")
    public Res logErr() {
        log.info("登录失败");
        return Res.err("登录失败");
    }

    /**
     * 登录成功的路径
     */
    @GetMapping("/suc")
    public Res logSuc() {
        log.info("登录成功");
        return Res.suc("登录成功");
    }

    /**
     * 退出登录之后重定向的路径
     */
    @GetMapping("/logout-suc")
    public Res logoutSuc() {
        log.info("退出登录成功");
        return new Res(204, "退出登录成功");
    }
}
```

### 返回值的封装类

> Java17的record类，别告诉我你还在使用Java8

```java
package com.example.mysecutiy.common;

public record Res(int code, String msg) {
    public static Res suc(String msg) {
        return new Res(1, msg);
    }

    public static Res err(String msg) {
        return new Res(0, msg);
    }
}
```

### 登录页面

> SpringSecurity的接口不能处理JSON数据，只能使用表单提交
>
> 使用JSON数据会一直提示登录失败，但估计是连参数都没有读取

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
<h3>使用SpringSecurity的登录接口，只能表单提交</h3>
<h3>账号:qwe，密码:123</h3>
<form action="/cao/toLogin" method="post">
    <label>
        账号：
        <input type="text" name="username">
    </label>
    <br>
    <label>
        密码：
        <input type="password" name="password">
    </label>
    <br>
    <label>
        记住我：
        <input type="checkbox" name="remember-me" id="remember-me">
    </label>
    <button type="submit">登录</button>
</form>
</body>
</html>
```

## 附录

### SpringBoot的启动类

```java
@SpringBootApplication
public class MySecutiyApplication {
    private static final Logger log = LoggerFactory.getLogger(MySecutiyApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MySecutiyApplication.class, args);
        log.info("登录地址：http://localhost:8080");
        log.info("登录地址2：http://localhost:8080/user/login.html");
    }

}
```

### 用于测试的页面

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>Hello World</title>
</head>
<body>
<h1>HELL_WORLD</h1>
<ul>
    <li><a href="/demo">测试，这里被放行</a></li>
    <li><a href="/demo/no">测试2，这里无权限</a></li>
</ul>
</body>
</html>
```

###  页面对应的接口

```java
/**
 * SpringSecurityTes测试
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public String hello() {
        return "NiH";
    }

    @GetMapping("/no")
    public String areYouOk() {
        return "areYouOk";
    }

}

```

