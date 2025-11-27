package example;

import config.FtlConfig;
import entiy.ColumnDo;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TsClassGenerator {
    /**
     * 下划线转驼峰
     *
     * @param input 大写下划线分割，如:DOG_PLAN
     * @return 大驼峰，如:DogPlan
     */
    private static String underlineToCamel(String input) {
        StringBuilder sb = new StringBuilder();
        String[] words = input.toLowerCase().split("_");
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }
        return sb.toString();
    }

    /**
     * 下划线转化为驼峰(但首字母小写)
     *
     * @param input 大写下划线分割，如:DOG_PLAN
     * @return 小驼峰，如:dogPlan
     */
    private static String underlineToCamel2(String input) {
        String res = underlineToCamel(input);
        // 首字母需要小写
        return res.substring(0, 1).toLowerCase() + res.substring(1);
    }

    // 生成TS类代码
    public static String generateTsClass(String tableName, List<ColumnDo> columnDos) {
        try {
            // 1. 准备模板数据
            Map<String, Object> dataModel = new HashMap<>();
            // 表名转驼峰（类名）
            dataModel.put("className", underlineToCamel(tableName));

            for (ColumnDo columnDo : columnDos) {
                columnDo.setColumnName(underlineToCamel2(columnDo.getColumnName()));
            }
            // 读取字段列表
            dataModel.put("columns", columnDos);

            // 2. 初始化FreeMarker配置
            Configuration cfg = FtlConfig.cfa;

            // 3. 加载模板并渲染
            Template template = cfg.getTemplate("ts_class.ftl");
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);

            // 4. 返回生成的TS代码
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "模板转化失败转换";
        }
    }

    public static void main(String[] args) {
        List<ColumnDo> columnDos = new ArrayList<>();
        columnDos.add(new ColumnDo("DOG_ID", "狗主键"));
        columnDos.add(new ColumnDo("DOG_NAME", "狗的名字"));
        columnDos.add(new ColumnDo("DOG_AGE", "狗的年龄"));
        System.out.println(generateTsClass("DOG", columnDos));
    }
}