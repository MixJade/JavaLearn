package com.chat.controller;

import com.chat.pojo.UserVo;
import com.chat.ws.ChatEndpoint;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 在聊天过程中所调用的接口
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @GetMapping("/getUsername")
    public String getUsername(HttpSession session) {
        UserVo userVo = (UserVo) session.getAttribute("user");
        if (userVo == null) return "noMan";
        return userVo.username();
    }

    @GetMapping("/syncHistoryMsg")
    public List<String> syncHistoryMsg() {
        return ChatEndpoint.getHistoryMsg();
    }

    @GetMapping("/getLoginUserList")
    public Set<String> getLoginUserList() {
        return ChatEndpoint.getLoginUserList();
    }
}

