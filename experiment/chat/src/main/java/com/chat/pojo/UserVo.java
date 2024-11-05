package com.chat.pojo;

/**
 * 在线用户
 *
 * @param username 用户名
 * @param imgColor 头像颜色
 * @param simpleNm 简称
 */
public record UserVo(String username, String imgColor, String simpleNm) {
}
