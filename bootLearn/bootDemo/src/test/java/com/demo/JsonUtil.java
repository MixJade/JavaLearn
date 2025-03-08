package com.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 封装JackSon工具
 *
 * @since 2024-5-14
 */
public class JsonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String objToStr(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            System.err.println("Json转String出错");
            return "";
        }
    }

    @SuppressWarnings("unused")
    public static <T> T strToObj(String s, Class<T> valueType) {
        try {
            return objectMapper.readValue(s, valueType);
        } catch (JsonProcessingException e) {
            System.err.println("String转Json出错");
            return null;
        }
    }

    public static <T> T strToObj(String s, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(s, valueTypeRef);
        } catch (JsonProcessingException e) {
            System.err.println("String转Json出错");
            return null;
        }
    }
}
