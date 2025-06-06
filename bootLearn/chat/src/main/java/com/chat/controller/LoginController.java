package com.chat.controller;

import com.chat.pojo.Result;
import com.chat.pojo.UserVo;
import com.chat.utils.ColorUtil;
import com.chat.utils.NameUtil;
import com.chat.ws.ChatEndpoint;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在登录页面所调用的接口
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/getRandomName")
    public String getRandomName() {
        return NameUtil.getName();
    }

    @GetMapping("/toLogin")
    public Result toLogin(@RequestParam("user") String user, HttpSession session) {
        if (user != null && !user.isBlank()) {
            if (ChatEndpoint.isDupName(user))
                return new Result(false, "名称重复");
            session.setAttribute("user", new UserVo(user,
                    ColorUtil.randomColor(),
                    user.substring(user.length() - 1)));
            log.info("用户【{}】登录", user);
            return new Result(true, "成功");
        } else {
            return new Result(false, "失败");
        }
    }
}

