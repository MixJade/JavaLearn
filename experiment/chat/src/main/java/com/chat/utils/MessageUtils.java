package com.chat.utils;

import com.chat.pojo.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 需要的工具类，用于转化信息格式
 */
public class MessageUtils {
    public static String getMessage(boolean isSystemMessage,String fromName,Object message){
        try {
            ResultMessage result = new ResultMessage(isSystemMessage,fromName,message);
            //把字符串转成json格式的字符串
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}

