package example;

import entiy.User;
import utils.FtlUtil;

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
        // 创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("user", new User("Tom", 20));

        // 加载模板, 输出文本
        String tempStr = FtlUtil.fillTempStr(dataModel, "common_template.ftl");
        // 输出文本
        System.out.println(tempStr);
    }
}