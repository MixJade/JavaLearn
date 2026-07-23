package com.chat.controller;

import com.chat.ima.ImaService;
import com.chat.pojo.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

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

    /**
     * 获取知识库文件内容（mediaId 来自搜索结果中的 a 标签）
     *
     * @param mediaId 文件 media_id（必填）
     * @return 文件内容，纯文本
     */
    @GetMapping("/getMedia")
    public ResponseEntity<String> getMedia(@RequestParam String mediaId) {
        String content = ImaService.getMediaContent(mediaId);
        if (content == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                .body(content);
    }
}
