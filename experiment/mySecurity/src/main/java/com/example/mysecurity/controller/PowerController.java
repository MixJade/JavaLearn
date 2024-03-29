package com.example.mysecurity.controller;

import com.example.mysecurity.common.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于权限校验的接口
 */
@RestController
@RequestMapping("/power")
public class PowerController {
    private static final Logger log = LoggerFactory.getLogger(PowerController.class);

    /**
     * 未登录的重定向
     */
    @GetMapping("/noLogin")
    public Res noLogin() {
        return new Res(401, "当前未登录");
    }

    /**
     * 登录失败的路径
     */
    @GetMapping("/err")
    public Res logErr() {
        log.info("登录失败");
        return Res.err("登录失败");
    }

    /**
     * 登录成功的路径
     */
    @GetMapping("/suc")
    public Res logSuc() {
        log.info("登录成功");
        return Res.suc("登录成功");
    }

    /**
     * 没有权限的路径
     */
    @GetMapping("/noPower")
    public Res noPower() {
        log.info("没有权限");
        return new Res(403, "无权访问");
    }

    /**
     * 退出登录之后重定向的路径
     */
    @GetMapping("/logout-suc")
    public Res logoutSuc() {
        log.info("退出登录成功");
        return new Res(204, "退出登录成功");
    }
}
