package com.forge.shiro;

import com.forge.shiro.authentic.MyRealmCheck;
import com.forge.shiro.realm.ClientRealm;
import com.forge.shiro.realm.EmployeeRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ShiroConfig {
    private EmployeeRealm employeeRealm;
    private ClientRealm clientRealm;

    @Autowired
    public void setEmployeeRealm(EmployeeRealm employeeRealm) {
        this.employeeRealm = employeeRealm;
    }

    @Autowired
    public void setClientRealm(ClientRealm clientRealm) {
        this.clientRealm = clientRealm;
    }

    /**
     * 配置securityManager
     *
     * @return DefaultWebSecurityManager对象
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DefaultWebSecurityManager getDefaultWebSecurityManage() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        // 加密对象存储在realm中，使用自己定义的MyRealmCheck
        securityManager.setAuthenticator(new MyRealmCheck());
        employeeRealm.setCredentialsMatcher(matcher);
        clientRealm.setCredentialsMatcher(matcher);
        List<Realm> realms = new ArrayList<>();
        realms.add(employeeRealm);
        realms.add(clientRealm);
        securityManager.setRealms(realms);
        // 记住我
        securityManager.setRememberMeManager(rememberMe());
        // 去掉JSESSIONID,防止重定向失败
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * Shiro的过滤器设置
     *
     * @return 过滤器
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        // 不认证可以访问的路径
        definition.addPathDefinition("/login/**", "anon");
        definition.addPathDefinition("/assets/**", "anon");
        definition.addPathDefinition("/picture/**", "anon");
        definition.addPathDefinition("/login.html", "anon");
        definition.addPathDefinition("/noPower.html", "anon");
        definition.addPathDefinition("/index.html", "anon");
        definition.addPathDefinition("/favicon.ico", "anon");
        // 退出登录
        definition.addPathDefinition("/logout", "logout");
        // 限定角色访问
        definition.addPathDefinition("/backstage/**", "roles[nurse]");
        // 需要进行登录认证的路径,一定要放在下面，不然前面的可能失效
        definition.addPathDefinition("/**", "authc");
        // 记住我的过滤器,一定要放在最下面，不然回忆起了但是请求已经处理了
        definition.addPathDefinition("/**", "user");
        return definition;
    }

    /**
     * remember管理器
     */
    public CookieRememberMeManager rememberMe() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberCookie());
        //设置加密密钥（16位），防止重启服务器后cookie失效
        rememberMeManager.setCipherKey("1234567890987654".getBytes());
        return rememberMeManager;
    }

    /**
     * rememberMe设置cookie
     */
    public SimpleCookie rememberCookie() {
        SimpleCookie cookie = new SimpleCookie("remember");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        return cookie;
    }

    /**
     * 重写重定向，去除JSESSIONID
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

}
