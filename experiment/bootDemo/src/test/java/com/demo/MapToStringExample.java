package com.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * JackSon的Map与String互转
 *
 * @author MixJade
 * @since 2024-3-26 11:08
 */
public class MapToStringExample {
    public static void main(String[] args) {
        try {
            // 创建一个示例的 Map 对象
            Map<String, String> originalMap = new HashMap<>();
            originalMap.put("key1", "value1");
            originalMap.put("key2", "123");

            // 将 Map 转换为 String
            ObjectMapper om = new ObjectMapper();
            String mapString = om.writeValueAsString(originalMap);

            System.out.println("Map 转换为 String：");
            System.out.println(mapString);

            // 将 String 转换为 Map
            Map<String, String> restoredMap = om.readValue(mapString, new TypeReference<>() {
            });

            System.out.println("String 转换为 Map：");
            System.out.println(restoredMap);

            // 将传参的String转为Map
            String mapString2 = "{\"1\":\"value1\",\"2\":\"123\"}";
            Map<String, String> stringMap = om.readValue(mapString2, new TypeReference<>() {
            });
            System.out.println("将传参的String转为Map");
            System.out.println(stringMap);
        } catch (JsonProcessingException e) {
            System.out.println("转化失败");
        }
    }
}