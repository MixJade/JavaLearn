package com.chat.llama.req;

/**
 * 消息内容
 *
 * @param role    角色：user / assistant / system
 * @param content 对话内容
 */
public record ChatMessage(String role, String content) {
}