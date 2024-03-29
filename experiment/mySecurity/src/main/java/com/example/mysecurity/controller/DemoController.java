package com.example.mysecurity.controller;

import com.example.mysecurity.dao.MyUser;
import com.example.mysecurity.service.INowUser;
import com.example.mysecurity.service.RoleConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringSecurityTes测试
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    INowUser nowUser;

    @Autowired
    public DemoController(INowUser nowUser) {
        this.nowUser = nowUser;
    }

    @GetMapping
    public String hello() {
        return "NiH";
    }

    @GetMapping("/no")
    public String areYouOk(Authentication authentication) {
        if (authentication.getPrincipal() instanceof MyUser myUser) {
            System.out.println("登录的用户信息7是：" + myUser);
        }
        return "areYouOk";
    }

    @GetMapping("/role")
    // 这个是要去启动类打开的
    // 可以放多个角色，只要有一个就能访问
    // 角色的写法与赋予角色时一样
    // @Secured(RoleConst.ADMIN2) // 只放一个时不用加花括号
    @Secured({RoleConst.ADMIN, RoleConst.ADMIN2})
    public String role() {
        // 可以如此获取当前登录的信息
        if (nowUser.getUser() instanceof MyUser myUser) {
            System.out.println("登录的用户信息是：" + myUser);
        }
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
