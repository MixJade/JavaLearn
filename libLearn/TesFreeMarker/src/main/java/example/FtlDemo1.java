package example;

import entiy.User;
import utils.FtlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FtlDemo1 {
    public static void main(String[] args) {
        // 创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("userList", getUsers());

        // 加载模板，输出文本
        String resStr = FtlUtil.fillTempStr(dataModel, "list_template.ftl");
        System.out.println(resStr);
    }

    private static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 20));
        users.add(new User("李四", 25));
        users.add(new User("王五", 55));
        return users;
    }
}