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
    // 启动AI
    @GetMapping("/openAi")
    public Result openAi() {
        return new Result(true, LlamaService.start());
    }

    // 检测是否存活
    @GetMapping("/isAlive")
    public Result isAlive() {
        return new Result(LlamaService.isAlive(), "alive");
    }

    // 停止AI
    @GetMapping("/stopAi")
    public Result stopAi() {
        return new Result(true, LlamaService.stop());
    }
}

