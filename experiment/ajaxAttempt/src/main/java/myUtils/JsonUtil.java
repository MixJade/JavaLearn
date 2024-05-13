package myUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 完全依赖JackSon，一个小工具
 *
 * @since 2023-6-3
 */
public class JsonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String toStr(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            System.err.println("Json转String出错");
            return "";
        }
    }

    public static <T> T toObj(String s, Class<T> valueType) {
        try {
            return objectMapper.readValue(s, valueType);
        } catch (JsonProcessingException e) {
            System.err.println("String转Json出错");
            return null;
        }
    }
}
