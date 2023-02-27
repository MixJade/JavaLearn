package com.chat.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 系统返回消息
 * @param isSystem 是否系统发送
 * @param fromName 发送者用户名
 * @param msg 具体消息
 */
public record SocketMsg(boolean isSystem,String fromName,Object msg){
    public static String getMsg(boolean isSystem,String fromName,Object message){
        try {
            SocketMsg result = new SocketMsg(isSystem,fromName,message);
            //把字符串转成json格式的字符串
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
