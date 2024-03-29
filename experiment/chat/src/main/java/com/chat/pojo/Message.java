package com.chat.pojo;

/**
 * 用户发送消息
 *
 * @param toName  接收者
 * @param message 信息
 */
public record Message(String toName, String message) {
}
