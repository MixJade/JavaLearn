package com.chat.pojo;

import jakarta.websocket.RemoteEndpoint.Basic;

/**
 * 在线用户
 *
 * @param username 用户名
 * @param basic    该用户的远程端点，用于向该用户发消息
 */
public record MsgUser(String username, Basic basic) {
}
