package tsClass;

import entiy.ColumnDo;
import entiy.User;
import utils.FtlUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java实体类转ts代码
 *
 * @since 2026-01-04 16:46:49
 */
public class JavaToTs {
    public static void main(String[] args) {
        // 1. 获取Class对象（3种方式可选，根据场景选择）
        Class<User> targetClass = User.class; // 方式1：已知类，直接通过.class获取（推荐，编译期确定）
        // 2. 获取类名
        String simpleClassName = targetClass.getSimpleName(); // 简单类名（仅类名）
        // 3. 获取当前类所有字段（getDeclaredFields()：所有访问权限，不包含父类）
        Field[] allFields = targetClass.getDeclaredFields();
        List<ColumnDo> columnDos = new ArrayList<>();
        for (Field field : allFields) {
            String fieldName = field.getName();
            columnDos.add(new ColumnDo(fieldName, ""));
        }
        System.out.println(generateTsClass(simpleClassName, columnDos));
    }

    public static String generateTsClass(String className, List<ColumnDo> columnDos) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("className", className);
        dataModel.put("columns", columnDos);
        // 加载模板并渲染, 返回生成的TS代码
        return FtlUtil.fillTempStr(dataModel, "ts_class.ftl");
    }
}
