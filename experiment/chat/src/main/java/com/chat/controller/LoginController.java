package com.chat.controller;

import com.chat.pojo.Result;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/toLogin")
    public Result toLogin(@RequestParam("user") String user, HttpSession session) {
        log.info("用户【{}】登录", user);
        if (user != null && !user.isBlank()) {
            session.setAttribute("user", user);
            return new Result(true, "成功");
        } else {
            return new Result(false, "失败");
        }
    }

    @GetMapping("/getUsername")
    public String getUsername(HttpSession session) {
        String username = (String) session.getAttribute("user");
        if (username == null) return "noMan";
        return username;
    }
}

