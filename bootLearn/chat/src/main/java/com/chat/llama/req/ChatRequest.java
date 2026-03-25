package com.chat.llama.req;

import java.util.List;

/**
 * 聊天消息封装
 *
 * @param model       模型名称
 * @param messages    对话消息列表
 * @param temperature 温度系数
 * @param max_tokens  最大token数
 * @param stream      是否流式输出
 */
public record ChatRequest(String model, List<ChatMessage> messages, double temperature, int max_tokens,
                          boolean stream) {
    public static ChatRequest buildReq(List<ChatMessage> messages) {
        return new ChatRequest("qwen3", messages, 0.7, 512, false);
    }
}