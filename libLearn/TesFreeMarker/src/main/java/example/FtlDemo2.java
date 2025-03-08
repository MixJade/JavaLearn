package example;

import config.FtlConfig;
import entiy.User;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker的常规用法
 *
 * @since 2024-08-20 14:44:27
 */
public class FtlDemo2 {

    public static void main(String[] args) {
        // 加载模板
        String tempStr = userTempStr(new User("Tom", 20));
        // 输出文本
        System.out.println(tempStr);
    }

    private static String userTempStr(User user) {
        try {
            // 创建配置类(顺便指定默认版本)
            Configuration cfa = FtlConfig.cfa;
            // 创建数据模型
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("user", user);

            // 加载模板
            Template template = cfa.getTemplate("common_template.ftl");

            // 输出文本
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (Exception e) {
            return "模板转化失败转换";
        }
    }
}