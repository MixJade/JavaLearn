package com.chat.pojo;

/**
 * 系统返回消息
 * @param isSystem 是否系统发送
 * @param fromName 发送者用户名
 * @param msg 具体消息
 */
public record ResultMessage(boolean isSystem,String fromName,Object msg){
}
