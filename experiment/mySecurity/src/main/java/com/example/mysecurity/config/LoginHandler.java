package com.example.mysecurity.config;

import com.example.mysecurity.common.Res;
import com.example.mysecurity.dao.MyUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.io.IOException;

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
        //      以下为一次尝试，可以看到这样存入上下文是不行的
        /*SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);*/
        // 开始重定向
        /*try {
            resp.sendRedirect(url);
        } catch (IOException e) {
            log.warn("重定向失败");
        }*/
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
