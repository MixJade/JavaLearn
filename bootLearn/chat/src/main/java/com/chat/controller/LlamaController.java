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
        return new Result(LlamaService.isAlive(), "alive");
    }
}

