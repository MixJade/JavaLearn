package example;

import entiy.ColumnDo;
import utils.ConvertCase;
import utils.FtlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成TS类代码
 *
 * @since 2025-11-27 16:06
 */
public class TsClassGen {
    public static String generateTsClass(String tableName, List<ColumnDo> columnDos) {
        // 1. 准备模板数据
        Map<String, Object> dataModel = new HashMap<>();
        // 表名转驼峰（类名）
        dataModel.put("className", ConvertCase.lSnakeToLCamel(tableName));
        // 读取字段列表
        for (ColumnDo columnDo : columnDos) {
            columnDo.setColumnName(ConvertCase.lSnakeToSCamel(columnDo.getColumnName()));
        }
        dataModel.put("columns", columnDos);

        // 3. 加载模板并渲染, 返回生成的TS代码
        return FtlUtil.fillTempStr(dataModel, "ts_class.ftl");
    }

    public static void main(String[] args) {
        List<ColumnDo> columnDos = new ArrayList<>();
        columnDos.add(new ColumnDo("DOG_ID", "狗主键"));
        columnDos.add(new ColumnDo("DOG_NAME", "狗的名字"));
        columnDos.add(new ColumnDo("DOG_AGE", "狗的年龄"));
        System.out.println(generateTsClass("DOG", columnDos));
    }
}