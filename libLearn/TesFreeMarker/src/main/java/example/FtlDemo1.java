package example;

import config.FtlConfig;
import entiy.User;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FtlDemo1 {

    public static void main(String[] args) {
        try {
            // 创建配置类(顺便指定默认版本)
            Configuration cfa = FtlConfig.cfa;
            // 创建数据模型
            Map<String, Object> dataModel = new HashMap<>();

            dataModel.put("userList", getUsers());

            // 加载模板
            Template template = cfa.getTemplate("list_template.ftl");

            // 输出文本
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);
            System.out.println(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 20));
        users.add(new User("李四", 25));
        users.add(new User("王五", 55));
        return users;
    }
}