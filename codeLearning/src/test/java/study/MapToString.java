package study;

import java.util.HashMap;
import java.util.Map;

/**
 * Map与String互相转化
 *
 * @since 2024-3-25 09:42
 */
public class MapToString {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");
        String mapString = map.toString();
        System.out.println("map转string:" + mapString);
        Map<String, String> stringMap = mapStringToMap(mapString);
        System.out.println("string转map(取值1):" + stringMap.get("1"));

        System.out.println("==================================");
        Map<String, String> stringMap2 = mapStringToMap("mapString");
        System.out.println("string转map(格式不对):" + stringMap2);
    }

    /**
     * 将string转为Map
     *
     * @param str :Map转化的字符串,传参格式:{1=value1, 2=value2}
     * @return HashMap
     */
    public static Map<String, String> mapStringToMap(String str) {
        Map<String, String> map = new HashMap<>();
        try {
            str = str.substring(1, str.length() - 1);
            String[] strings = str.split(",");
            for (String str1 : strings) {
                String key = str1.split("=")[0];
                String value = str1.split("=")[1];
                // 去掉头部空格
                String key1 = key.trim();
                String value1 = value.trim();
                map.put(key1, value1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("错误:待转为map的字符串不对");
        }
        return map;
    }
}
