package com.chat.controller;

import com.chat.llama.LlamaService;
import com.chat.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI对话接口
 */
@RestController
@RequestMapping("/api/llama")
public class LlamaController {
    // 检测是否存活
    @GetMapping("/isAlive")
    public Result isAlive() {
        boolean alive = LlamaService.isAlive();
        return new Result(alive, alive ? "alive" : "not alive");
    }

    // 切换存活状态
    @GetMapping("/toggleAlive")
    public Result toggleAlive() {
        boolean newStatus = LlamaService.toggleAlive();
        return new Result(newStatus, newStatus ? "已切换为存活" : "已切换为停止");
    }
}

