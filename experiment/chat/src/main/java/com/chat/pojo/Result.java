package com.chat.pojo;

/**
 * 登录之后响应数据
 *
 * @param flag 登录成功
 * @param msg  结果提示
 */
public record Result(boolean flag, String msg) {
}
