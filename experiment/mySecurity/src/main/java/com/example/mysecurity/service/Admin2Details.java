package com.example.mysecurity.service;

import com.example.mysecurity.dao.MyUser;
import com.example.mysecurity.dao.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
