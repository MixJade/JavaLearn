package study;

import java.lang.reflect.Field;

/**
 * 反射获取Java类名+字段
 *
 * @since 2026-01-04 16:41:55
 */
public class ReflectField {
    public static void main(String[] args) {
        // 1. 获取Class对象
        Class<User> targetClass = User.class;
        // 2. 获取类名（两种常用格式）
        String fullClassName = targetClass.getName(); // 全限定类名（包名+类名）
        String simpleClassName = targetClass.getSimpleName(); // 简单类名（仅类名）
        // 3. 打印类名信息
        System.out.println("===== 类名信息 =====");
        System.out.println("全限定类名：" + fullClassName);
        System.out.println("简单类名：" + simpleClassName);
        // 4. 获取当前类所有字段（getDeclaredFields()：所有访问权限，不包含父类）
        Field[] allFields = targetClass.getDeclaredFields();
        // 5. 遍历字段，获取每个字段的名称
        System.out.println("\n===== 该类下所有字段名 =====");
        if (allFields.length == 0) {
            System.out.println("该类无定义字段");
            return;
        }
        for (Field field : allFields) {
            // 核心：Field.getName() 获取字段名
            String fieldName = field.getName();
            // 可选：打印字段的访问修饰符、类型（Java 17兼容）
            String fieldType = field.getType().getSimpleName();
            System.out.println("字段名：" + fieldName + "，字段类型：" + fieldType);
        }
    }
}

record User(String username, String password, int age, boolean sex, float height){
}