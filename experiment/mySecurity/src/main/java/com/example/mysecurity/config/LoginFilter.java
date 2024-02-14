package com.example.mysecurity.config;

import com.example.mysecurity.dao.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义的登录处理器，让Security用Json校验密码
 *
 * @since : 2023-06-05 09:57
 */
//@Component // 直接New
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    public LoginFilter() {
        // 将登录的校验器放在这里
        super(new ProviderManager(new LoginProvider()));
        // 访问到拦截器的路径
        setFilterProcessesUrl("/cao/toLogin");
        // 登录成功处理器
        // setAuthenticationSuccessHandler(new LoginSucHandler("/power/suc"));
        LoginHandler loginHandler=new LoginHandler();
        setAuthenticationSuccessHandler(loginHandler);
        // 登录失败处理器
        // setAuthenticationFailureHandler(new LoginErrHandler("/power/err"));
        setAuthenticationFailureHandler(loginHandler);
        // 设置记住我
        setRememberMeServices(new RememberMeService());
    }

    //    /**
    //     * 自己注入配置，相关的bean已在MySecurityConfig.java中配置完成
    //     */
    //    @Autowired
    //    @Override
    //    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    //        super.setAuthenticationManager(authenticationManager);
    //    }

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
