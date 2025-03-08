package com.example.mysecurity.service;

import com.example.mysecurity.dao.MyUser;
import com.example.mysecurity.dao.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
