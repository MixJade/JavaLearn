package testJson.readJsData;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试使用JackSon读取js文件中的json
 *
 * @since 2024-11-24 19:23:38
 */
public class TestReadJsData {
    public static void main(String[] args) throws IOException {
        String string = Files.readString(Path.of("src/main/java/testJson/readJsData/测试的JS文件.js"));
        // System.out.println(string);
        Pattern p = Pattern.compile("(?<=const myData = )([\\s\\S]*?)(?=;)");
        Matcher m = p.matcher(string);
        while (m.find()) {
            String group = m.group();
            // System.out.println(group);
            // 配置参见 https://blog.csdn.net/u013066244/article/details/119028446
            ObjectMapper objectMapper = JsonMapper.builder()
                    // 设为false: 转string属性名称不带双引号
                    .disable(JsonWriteFeature.QUOTE_FIELD_NAMES)
                    // 反序列化是是否允许属性名称不带双引号
                    .enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES)
                    // 支持结尾逗号
                    .enable(JsonReadFeature.ALLOW_TRAILING_COMMA)
                    .build();
            List<DataGroup> dataGroup = objectMapper.readValue(group, new TypeReference<>() {
            });
            System.out.println(dataGroup.size());
            System.out.println(objectMapper.writeValueAsString(dataGroup));
        }
    }
}

@SuppressWarnings("unused")
class DataGroup {
    public String longNav;
    public AGroup mainBox;
    public AGroup bigSide;
    public AGroup bottom1;
    public AGroup bottom2;
    public AGroup bottom3;
    public String updateTime;
}

@SuppressWarnings("unused")
class AGroup {
    public String name;
    public List<DefA> ul;
}

@SuppressWarnings("unused")
class DefA {
    public String href;
    public String name;
    public String remark;
    public String pwd;
}
