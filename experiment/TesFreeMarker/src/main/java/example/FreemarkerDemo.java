package example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreemarkerDemo {

    public static void main(String[] args) throws IOException, TemplateException {
        // 创建配置类
        Configuration cfa = new Configuration(Configuration.getVersion());
        // 设置模板路径，这里我们设置的是class路径下的模板文件夹
        cfa.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "");
        // 设置字符集
        cfa.setDefaultEncoding("UTF-8");

        // 创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        List<User> users = new ArrayList<>();
        users.add(new User("Tom", 20));
        users.add(new User("Jerry", 25));
        dataModel.put("userList", users);

        // 加载模板
        Template template = cfa.getTemplate("example/list_template.ftl");

        // 输出文本
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        System.out.println(writer);
    }
}