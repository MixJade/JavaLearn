package com.chat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 封装JackSon工具
 *
 * @since 2024-5-14
 */
public class JsonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T strToObj(String s, Class<T> valueType) {
        try {
            return objectMapper.readValue(s, valueType);
        } catch (IOException e) {
            System.err.println("String转Json出错");
            return null;
        }
    }

    public static String objToStr(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (IOException e) {
            System.err.println("Json转String出错");
            return "";
        }
    }
}
