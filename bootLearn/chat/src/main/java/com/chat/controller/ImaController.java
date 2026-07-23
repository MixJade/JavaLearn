package com.chat.controller;

import com.chat.ima.ImaService;
import com.chat.pojo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * IMA 知识库接口
 */
@RestController
@RequestMapping("/api/ima")
public class ImaController {

    /**
     * 检查 IMA 对话是否开启
     */
    @GetMapping("/isAlive")
    public Result isAlive() {
        boolean alive = ImaService.isAlive();
        return new Result(alive, alive ? "alive" : "not alive");
    }

    /**
     * 切换 IMA 对话开关
     */
    @GetMapping("/toggleAlive")
    public Result toggleAlive() {
        boolean newStatus = ImaService.toggleAlive();
        return new Result(newStatus, newStatus ? "已开启 IMA 对话" : "已关闭 IMA 对话");
    }
}
