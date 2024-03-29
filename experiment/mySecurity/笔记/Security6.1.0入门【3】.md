# Security6.1.0入门【3】

> 主要说明登录拦截器的使用，以及记住我的功能
>
> 这里在后面使用redis+token可以极大地优化

## 登录过滤器

> 这里不用compont的注解，因为我会在配置类里面直接new

* 注意：这里在无参构造里使用的处理器是同一个，因为我用一个类把那两个接口给实现了
* 登录的校验器也在这里设置，校验器与登录拦截器的关系十分密切，功能相互耦合
* 记住我的remember参数要在校验登录的方法里设置，因为从这出去的request没有请求体的JSON

```java
/**
 * 自定义的登录处理器，让Security用Json校验密码
 *
 * @since : 2023-06-05 09:57
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    public LoginFilter() {
        // 将登录的校验器放在这里
        super(new ProviderManager(new LoginProvider()));
        // 访问到拦截器的路径
        setFilterProcessesUrl("/cao/toLogin");
        // 登录成功处理器
        LoginHandler loginHandler=new LoginHandler();
        setAuthenticationSuccessHandler(loginHandler);
        // 登录失败处理器
        setAuthenticationFailureHandler(loginHandler);
        // 设置记住我
        setRememberMeServices(new RememberMeService());
    }

    /***
     * 重写账号密码的处理，让其可以接收Json
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException {
        if (req.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            log.info("【自定义的登录过滤器启动】");
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest;
            try (InputStream is = req.getInputStream()) {
                Login login = mapper.readValue(is, Login.class);
                log.info("登录过滤器收到的参数是:\n" + login);
                // 设置记住我，这里会在自己的写记住我服务里面取出
                req.setAttribute("rememberMe",login.remember());
                // 事实上，这里并不是设置用户名与密码，而是主角（可以为Object)与凭证
                authRequest = new UsernamePasswordAuthenticationToken(login, login.password());
                setDetails(req, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                log.warn("Json转化失败");
                authRequest = new UsernamePasswordAuthenticationToken("", "");
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            log.info("【启用原来的登录过滤器】");
            return super.attemptAuthentication(req, resp);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("登录转成功处理器");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
```

## 登录的校验管理器

* 直接在拦截器里new
* 通过提供不同的userDetails来实现分表登录
* 最后是授权持久化那里，以后可以通过redis存储token，这样以后连rememberMe都不用

```java
/**
 * 想要实现分表登录
 */
// @Component // 直接new就不用注册
public class LoginProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(LoginProvider.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginProvider() {
        // 让用户名未找到的异常不被转化为凭证错误异常
        setHideUserNotFoundExceptions(false);
    }

    /**
     * 校验密码有效性
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("校验密码有效性");
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("接收到的凭证为空");
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("密码未与数据库匹配");
        }
    }

    /**
     * 根据用户名获取用户
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getPrincipal() instanceof Login login) {
            log.info("角色：{}，用户名:{}", login.roleType(), login.username());
            if (login.roleType() == RoleEnum.ADMIN2) {
                return (new Admin2Details()).loadUserByUsername(login.username());
            } else if (login.roleType() == RoleEnum.ADMIN) {
                return (new AdminDetails()).loadUserByUsername(login.username());
            }
        }
        log.warn("你好像在用传统方式登录");
        return (new AdminDetails()).loadUserByUsername(username);
    }

    /**
     * 授权持久化
     */
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        log.info("授权持久化.");
        return super.createSuccessAuthentication(principal, authentication, user);
    }


}
```
## 登录处理器

> 处理登录失败与成功

* 注意要在这里手动的将用户信息存入session，不然会出现明明登录成功了，但却没有登录信息
* 与以前相比，这里不再重定向，而是直接向前端写入返回信息

```java
/**
 * 自定义登录成功的处理器
 */
public class LoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    /**
     * 登录成功
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) {
        // 其实登录成功处理器的接口还有另外一个方法，但那是default方法，就不必重载
        // 反正登录成功也是来这里
        log.info("登录成功处理器调用1");
        // 下面这个获取信息眼熟不？
        if (authentication.getPrincipal() instanceof MyUser myUser) {
            log.info("登录的用户信息是：\n" + myUser);
        }
        // SecurityContext在设置Authentication的时候并不会自动写入Session，
        // 读的时候却会根据Session判断，所以需要手动写入一次，否则下一次刷新时SecurityContext是新创建的实例。
        req.getSession()
                .setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());
        // 直接返回Json
        writeJSON(Res.suc("登录成功"), resp);
    }

    /***
     * 登录失败
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException ae) {
        log.info("这是报错的信息，但一般不用：\n"+ae.getMessage());
        if (ae instanceof UsernameNotFoundException) {
            writeJSON(Res.err("用户名未找到"), resp);
        } else if (ae instanceof BadCredentialsException) {
            writeJSON(Res.err("密码错误"), resp);
        } else {
            writeJSON(Res.err("登录失败"), resp);
        }
    }


    /**
     * 直接返回JSON
     *
     * @param res 返回值封装
     */
    private void writeJSON(Res res, HttpServletResponse resp) {
        try {
            // 写入JSON
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(res);
            // 相应数据
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(jsonString);
        } catch (IOException e) {
            log.warn("JSON转化失败");
        }
    }
}
```

## 记住我服务类

* 主要是在onLoginSuccess方法中最后那一句setCookie，这里可以自己设置cookie的格式
* 再次进入网站会调用processAutoLoginCookie方法，这会解析你之前设置的cookie

```java
/**
 * 记住我
 */
public class RememberMeService extends AbstractRememberMeServices {
    private final Log log = LogFactory.getLog(this.getClass());
    private int lifeHour = 3; // 记住我3个小时

    public RememberMeService() {
        super("MixJade", new AdminDetails());
    }

    @Override
    protected void onLoginSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication sucAut) {
        log.info("登录成功的记住我处理");
        if (sucAut.getPrincipal() instanceof MyUser myUser) {
            String username = myUser.username();
            String password = myUser.password();
            RoleEnum roleEnum = myUser.roleEnum();
            if (!StringUtils.hasLength(username)) {
                log.info("用户名为空");
                return;
            }
            long expiryTime = System.currentTimeMillis();
            expiryTime += 1000L * 3600L * (long) (lifeHour < 0 ? 24 : lifeHour);
            String signatureValue = this.makeTokenSignature(expiryTime, username, password, roleEnum.name());
            setCookie(new String[]{username, Long.toString(expiryTime), roleEnum.name(), "MD5", signatureValue}, lifeHour * 3600, req, resp);
        }
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest req, String parameter) {
        log.info("自定义RememberMe处理;");
        if (req.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            log.info("芝士JSON");
            boolean rememberMe = (boolean) req.getAttribute("rememberMe");
            log.info("记住我处理器收到的参数是:" + rememberMe);
            return rememberMe;
        } else return false;
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest req, HttpServletResponse resp) throws RememberMeAuthenticationException, UsernameNotFoundException {
        if (cookieTokens.length != 4 && cookieTokens.length != 5) {
            throw new InvalidCookieException("这Cookie不对啊");
        }
        log.info("收到的cookie" + Arrays.toString(cookieTokens));
        String name = cookieTokens[0];
        // 事实上还要判断当前cookie是否过期
        if (cookieTokens[2].equals(RoleEnum.ADMIN2.name())) {
            return new Admin2Details().loadUserByUsername(name);
        } else return new AdminDetails().loadUserByUsername(name);
    }

    /***
     * 设置token内容
     */
    private String makeTokenSignature(long expiryTime, String username, String password, String role) {
        String data = username + ":" + expiryTime + ":" + password + ":" + role + ":" + this.getKey();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(Hex.encode(digest.digest(data.getBytes())));
        } catch (NoSuchAlgorithmException var8) {
            throw new IllegalStateException("Token加密失败");
        }
    }

    /**
     * 设置cookie存活时间
     */
    public void setLifeHour(int lifeHour) {
        this.lifeHour = lifeHour;
    }
}
```

## 配置类

* 记住我服务除了在登录过滤器那里，也得在这里开始
* 因为许多登录相关的东西都配置到登录过滤器那里，所以这个配置类少了很多

```java
/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // 开启角色验证的注解
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] quiet = {"/", "/index.html", "/user/**"}; // 静态资源，以数组的形式抽离可变参数
        String[] rearEnd = {"/demo"}; // 后端接口
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(quiet).permitAll() // 放行的静态资源路径
                        .requestMatchers(rearEnd).permitAll() // 放行的后端接口路径
                        .requestMatchers("/cao/toLogin").anonymous() // 登录接口只允许匿名访问
                        .anyRequest().authenticated() // 其他地址的访问均需验证权限
                )
                // 这里很多配置都移到了登录过滤器的配置里面
                .formLogin((form) -> form.loginPage("/power/noLogin") // 设置未登录的重定向路径
                        .permitAll() // 尽管我已经在前面配置了，但还是放行留作象征
                )
                // 自己的登录过滤器
                .addFilterAt(new LoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .rememberMe(remember -> remember.rememberMeServices(new RememberMeService()))
                // 没有权限时的重定向路径
                .exceptionHandling(e -> e.accessDeniedPage("/power/noPower"))
                .csrf(CsrfConfigurer::disable) // 关闭跨站攻击防护
                .logout((logout) -> logout.logoutUrl("/cao/logout") // 退出登录的路径，不需要自己的接口
                        .logoutSuccessUrl("/power/logout-suc") // 退出登录成功的重定向路径
                        .permitAll() // 放行路径，理由同上
                );

        return http.build();
    }
}
```

## 附录

* 展示一些不那么重要的类
* 但是这个Demo的一部分

### 两个UserDetailsService

* AdminDetails，最普通的一个，主要是在一些时候作为默认的UserDetailsService使用

```java
/**
 * 自定义登录逻辑
 */
@Service
public class AdminDetails implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(AdminDetails.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("从数据库根据用户名查询数据");
        // 用户名匹配，这里的要从数据库查才行
        if (!"qwe".equals(username)) {
            log.warn("没有这个用户");
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 校验所需要的密码，前端如果输入123才能匹配成功
        String password = new BCryptPasswordEncoder().encode("123");
        // 授予角色
        List<String> roles = new ArrayList<>();
        roles.add(RoleConst.USER);
        roles.add(RoleConst.ADMIN);
        // 这个工具方法除了可以接收List<String>，还可以接收String[]
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        return new MyUser(username, password, authorities, RoleEnum.ADMIN);
    }
}
```

* Admin2Details，在实际中，表示另外一张表，通过token中自己定义的枚举进行区分

```java
/**
 * 自定义登录逻辑
 */
@Service
public class Admin2Details implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(Admin2Details.class);

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("从数据库根据用户名【{}】查询数据002", username);
        if (!"qwe234".equals(username)) throw new UsernameNotFoundException("用户名不存在");
        String password = new BCryptPasswordEncoder().encode("123");
        // 授予角色
        return new MyUser(username, password, AuthorityUtils.createAuthorityList(RoleConst.ADMIN2), RoleEnum.ADMIN2);
    }
}
```

### 用于区分不同表的枚举

* 建议这里与UserDetailsService的名字保持一致，因为很多时候，它代表了不同表的用户
* 不太建议将不同类型的用户用不同的表隔离起来，这会导致有些时候在表中定义操作者不太方便
* 我想到的方案是，专门定义一张登录账号密码表，然后存放不同用户与其所对应的关系，但这样又可能导致用户名的重复问题

```java
public enum RoleEnum {
    ADMIN,ADMIN2
}
```

### 登录时的记录类

* 用于接收登录时所传递的参数
* 在登录时会定义角色参数，然后经过登录过滤器传递到登录的校验管理器

```java
public record Login(String username, String password, boolean remember, RoleEnum roleType) {
}
```

### 登录成功之后的记录类

> 这会作为参数集合在过滤器中传递

```java
public record MyUser(String username,
                     String password,
                     List<GrantedAuthority> auth,
                     RoleEnum roleEnum) implements UserDetails {
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

### 前端的页面

* 登录页面，事实上，一到这个页面就会发起登录请求
* 完全不需要使用表单登录，表单登录是传统方式，不建议这样

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
        <input type="text" name="username" value="qwe">
    </label>
    <br>
    <label>
        密码：
        <input type="password" name="password" value="123">
    </label>
    <br>
    <label>
        记住我：
        <input type="checkbox" name="remember-me" id="remember-me">
    </label>
    <button type="submit">登录</button>
</form>
<br><a href="../index.html">返回首页</a>
</body>
<script>
    const json = {
        "username": "qwe",
        "password": "123",
        "remember": true,
        "roleType": "ADMIN"
    }
    // 以下是另外一位角色的登录
    // const json = {
    //     "username": "qwe234",
    //     "password": "123",
    //     "remember": true,
    //     "roleType": "ADMIN2"
    // }

    ajaxPost("/cao/toLogin", json).then(resp => {
        console.log("测试的相应", resp)
        console.log("测试的JSON解析", resp.code)
    })

    // 定义的原生ajax发送post请求
    async function ajaxPost(url, jsonData) {
        return new Promise(resolve => {
            // 1. 转化数据为string
            const strData = JSON.stringify(jsonData)
            // 2. 创建核心对象
            const xhr = new XMLHttpRequest();
            // 4. 获取响应
            xhr.onreadystatechange = () => {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    resolve(JSON.parse(xhr.responseText))
                }
            }
            // 3. 发送请求
            xhr.open("POST", url, true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(strData);
        })
    }
</script>
</html>
```

### 主页面

* 与以前一样

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

