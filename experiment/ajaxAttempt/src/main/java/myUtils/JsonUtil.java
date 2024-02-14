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

    public static String toStr(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T toObj(String s, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(s, valueType);
    }
}
