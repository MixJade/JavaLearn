package com.example.mysecurity.config;

import com.example.mysecurity.dao.MyUser;
import com.example.mysecurity.dao.RoleEnum;
import com.example.mysecurity.service.Admin2Details;
import com.example.mysecurity.service.AdminDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
