# Security6.1.0入门【2】

> 之前只是改了一下配置类，现在开始整一些实现类与其它的功能
>
> 这是第二章，只展示所改动的类，部分未展示的代码看前一篇笔记

## 小Demo

* 关于登录成功之后的处理器
* 实现登录逻辑的类，但这个登录接口后面会去掉，应该在自己的接口之中赋予权限，而不是调用Security的接口
* 以及基于角色的权限控制

### 配置类

* 自己实现了userDetailsService接口之后，就不需要在配置类里面配置userDetailsService的Bean了
* 取消掉登录成功之后的重定向配置，转而使用自己实现的登录成功控制器
* 新增没有权限时的重定向路径，重定向到特定接口，以返回一个特殊json数据，这个将是权限拦截的关键

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
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(quiet).permitAll() // 放行的静态资源路径
                        .requestMatchers(rearEnd).permitAll() // 放行的后端接口路径
                        // 校验是否具有角色，这里去掉ADMIN2后面的2就可以运行，后面用注解测试
                        // 注意：需要权限的接口不能被放行，不然角色验证失效
                        // .requestMatchers("/demo/role").hasAnyRole("ADMIN", "USER")
                        // .requestMatchers("/demo/role2").hasRole("ADMIN2")
                        .anyRequest().authenticated() // 其他地址的访问均需验证权限
                )
                .formLogin((form) -> form.loginPage("/power/noLogin") // 设置未登录的重定向路径
                        .usernameParameter("username") // 调试：登录时的用户名参数
                        .passwordParameter("password") // 调试：登录时的密码参数
                        .loginProcessingUrl("/cao/toLogin") // 调试：登录时的参数提交地址，不需要自己的接口
                        // 登录成功路径，但如果是从未登录的地方跳转来的，会跳回去，不建议使用
                        //  .successForwardUrl("/power/suc")
                        //  .defaultSuccessUrl("/power/suc", true) // 登录成功之后跳转的路径
                        .successHandler(new LoginSucHandler("/power/suc")) // 自己写的登录成功处理器
                        .failureUrl("/power/err") // 登录失败的重定向路径
                        // 自己写的登录失败处理器，但这样的话就得将对应重定向路径放行
                        // .failureHandler(new LoginErrHandler("/power/err"))
                        .permitAll() // 将这些路径放行，不然会重定向过多
                )
                // .formLogin(withDefaults()) // 为了方便入门，使用默认的登录页面
                .csrf(CsrfConfigurer::disable) // 关闭跨站攻击防护
                .rememberMe(withDefaults()) // 关于记住我，默认保存两周，登录时参数名的默认值为remember-me
                // 没有权限时的重定向路径
                .exceptionHandling(e->e.accessDeniedPage("/power/noPower"))
                .logout((logout) -> logout.logoutUrl("/cao/logout") // 退出登录的路径，不需要自己的接口
                        .logoutSuccessUrl("/power/logout-suc") // 退出登录成功的重定向路径
                        .permitAll() // 放行路径，理由同上
                );

        return http.build();
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

### 登录成功处理器

> 这个获取登录人员的信息会很有用，也许以后会将之存入数据库，然后根据token获取

* 主要就是实现AuthenticationSuccessHandler接口
* 然后在配置类中进行引入

```java
/**
 * 自定义登录成功的处理器
 *
 * @param url 登录之后的重定向路径
 */
public record LoginSucHandler(String url) implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginSucHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 其实登录成功处理器的接口还有另外一个方法，但那是default方法，就不必重载
        // 反正登录成功也是来这里
        log.info("登录成功处理器调用1");
        // 下面这个获取信息眼熟不？
        if (authentication.getPrincipal() instanceof MyUser myUser) {
            System.out.println("登录的用户信息是：" + myUser);
        }
        // 开始重定向
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            log.warn("重定向失败");
        }
    }
}
```

### 用户信息封装类

> 实现UserDetails接口，将那几个返回boolean的方法改为返回true

```java
public record MyUser(String username, String password, List<GrantedAuthority> auth) implements UserDetails {

    // 授权信息集合
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auth;
    }

    // 获取密码
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    // 获取用户名
    public String getUsername() {
        return username;
    }

    @Override
    // 用户的帐户是否未过期。即未过期则返回true
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // 用户是否未锁定。无法对锁定的用户进行身份验证，如果用户未被锁定，则返回true
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // 用户的凭据（密码）是否未过期，即未过期则返回true
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // 用户是启用还是禁用，如果启用了用户则返回true
    public boolean isEnabled() {
        return true;
    }
}
```

### 处理登录逻辑的类

> 但目前登录的接口依然仰仗SpringSecurity自带的
>
> 不过这样好在只需要打上Service注解，就不需要去其它地方引用

* 角色的授予主要靠AuthorityUtils.createAuthorityList这个工具类的方法
* 这个方法除了接收列表，还可以接收可变参数

```java
/**
 * 自定义登录逻辑
 */
@Service
public class LoginServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("这里逻辑被调用");
        // 用户名匹配，这里的要从数据库查才行
        if (!"qwe".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 校验所需要的密码，前端如果输入123才能匹配成功
        String password = new BCryptPasswordEncoder().encode("123");
        // 授予角色
        List<String> roles = new ArrayList<>();
        roles.add(RoleConst.USER);
        roles.add(RoleConst.ADMIN);
        // 这个工具方法除了可以接收List<String>，还可以接收String[]
        return new MyUser(username, password, AuthorityUtils.createAuthorityList(roles));
    }
}
```

### 角色常量

> 相信我，这些东西封装成常量更节省体力
>
> 并且常量接口相比于枚举，可以直接在注解中使用

```java
package com.example.mysecutiy.service;

public interface RoleConst {
    // 赋予角色时强制要求前面加上ROLE_
    // 验证时不需要，验证角色时，会自动在前面加ROLE_
    String USER="ROLE_USER",
            ADMIN="ROLE_ADMIN",
            ADMIN2="ROLE_ADMIN2";
}
```

### 启动类的注解

> @EnableMethodSecurity(securedEnabled = true) // 开启角色验证的注解

* 这个Secured注解必须手动开启，这是校验角色的注解
* 里面的角色是只要有一个就行，比Shiro方便许多

```java
/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // 开启角色验证的注解
public class SecurityConfig {
    // ...乱七八糟的配置信息
}
```

### 权限返回控制器的改变

> 新增：noPower，没有权限时的重定向接口，
>
> 返回一个特定的Json，用于前端弹出警告提示

```java
package com.example.mysecutiy.controller;

import com.example.mysecutiy.common.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 没有权限的路径
     */
    @GetMapping("/noPower")
    public Res noPower() {
        log.info("没有权限");
        return new Res(403, "无权访问");
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

### Demo控制器的变化

* 注解PreAuthorize与Secured的使用
* 其中PreAuthorize是默认开启的

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

    @GetMapping("/role")
    // 这个是要去启动类打开的
    // 可以放多个角色，只要有一个就能访问
    // 角色的写法与赋予角色时一样
    // @Secured(RoleConst.ADMIN2) // 只放一个时不用加花括号
    @Secured({RoleConst.ADMIN, RoleConst.ADMIN2})
    public String role() {
        return "YES_ADMIN";
    }

    @GetMapping("/role2")
    // 这个是默认开启的，但是看起来比较繁琐
    // 注意它的写法与配置类的角色权限控制的写法相同
    @PreAuthorize("hasRole('ADMIN2')")
    // 其实还有PostAuthorize，即：方法执行之后判断权限，基本不用
    public String role2() {
        return "YES_ADMIN2";
    }
}
```

### 新的主页

> 多加了几个路径

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>Hello World</title>
</head>
<body>
<h1>HELL_WORLD</h1>
<h3>功能测试</h3>
<ul>
    <li><a href="/demo">测试，这里被放行</a></li>
    <li><a href="/demo/no">测试2，这里要登录</a></li>
    <li><a href="/demo/role">测试3，这里要角色，我有这个角色</a></li>
    <li><a href="/demo/role2">测试4，这里要角色，但没有这个角色</a></li>
</ul>
<h3>特殊的路径</h3>
<ul>
    <li><a href="/user/login.html">跳转登录页面</a></li>
    <li><a href="/cao/logout">退出登录</a></li>
</ul>
</body>
</html>
```

