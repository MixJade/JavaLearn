package com.example.mysecurity.config;

import com.example.mysecurity.dao.Login;
import com.example.mysecurity.dao.RoleEnum;
import com.example.mysecurity.service.Admin2Details;
import com.example.mysecurity.service.AdminDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
