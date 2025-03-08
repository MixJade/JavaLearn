package com.example.mysecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true) // 开启角色验证的注解
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] quiet = {"/", "/index.html", "/user/**"}; // 静态资源，以数组的形式抽离可变参数
        String[] rearEnd = {"/demo"}; // 后端接口
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(quiet).permitAll() // 放行的静态资源路径
                        .requestMatchers(rearEnd).permitAll() // 放行的后端接口路径
                        .requestMatchers("/cao/toLogin").anonymous() // 登录接口只允许匿名访问
                        // 校验是否具有角色，这里去掉ADMIN2后面的2就可以运行，后面用注解测试
                        // 注意：需要权限的接口不能被放行，不然角色验证失效
                        // .requestMatchers("/demo/role").hasAnyRole("ADMIN", "USER")
                        // .requestMatchers("/demo/role2").hasRole("ADMIN2")
                        .anyRequest().authenticated() // 其他地址的访问均需验证权限
                )
                // 这里很多配置都移到了登录过滤器的配置里面
                .formLogin((form) -> form.loginPage("/power/noLogin") // 设置未登录的重定向路径
                        .permitAll() // 尽管我已经在前面配置了，但还是放行留作象征
                )
                // 自己的登录过滤器
                .addFilterAt(new LoginFilter(), UsernamePasswordAuthenticationFilter.class)
                // 因为自己配置了登录过滤器，所以这里的form不需要了
                /*.formLogin((form) -> form.loginPage("/power/noLogin") // 设置未登录的重定向路径
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
                )*/
                // .formLogin(withDefaults()) // 为了方便入门，使用默认的登录页面
                // .rememberMe(withDefaults()) // 关于记住我，默认保存两周，登录时参数名的默认值为remember-me
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

    // 重写了AbstractUserDetailsAuthenticationProvider，这里不太需要了
    ///**
    // * 密码器，强散列哈希加密，开启加密
    // */
    //@Bean
    //PasswordEncoder passwordEncoder() {
    //    return new BCryptPasswordEncoder();
    //}

    ///**
    // * 登录控制器的Bean
    // */
    //@Bean
    //LoginFilter loginFilter() {
    //    LoginFilter loginFilter = new LoginFilter();
    //    // 访问到拦截器的路径
    //    loginFilter.setFilterProcessesUrl("/cao/toLogin");
    //    // 登录成功处理器
    //    loginFilter.setAuthenticationSuccessHandler(new LoginSucHandler("/power/suc"));
    //    // 登录失败处理器，原来的form被干掉了
    //    loginFilter.setAuthenticationFailureHandler(new LoginErrHandler("/power/err"));
    //    return loginFilter;
    //}

    //  随着重写AbstractUserDetailsAuthenticationProvider，这里也不需要了
    //@Bean
    //public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    //    return authenticationConfiguration.getAuthenticationManager();
    //}

    // 这里必须注释掉，因为我直接定义了UserDetailsService的实现类
    /*    @Bean
        public UserDetailsService userDetailsService() {
            // 下面是设置密码，这里主要是加密的示例，正常要从数据库读取
            UserDetails user =
                    User.withUsername("qwe")
                            .password(new BCryptPasswordEncoder().encode("123"))
                            .roles("USER")
                            .build();

            return new InMemoryUserDetailsManager(user);
        }*/
}
