# OR多组条件查询

> 2024年5月11日11:54:00

## 一、常规运用

使用OR关键字进行多组条件查询。

以下是查询student表中age=18、grade=1，与age=23、grade=2的数据的SQL示例：

```sql
SELECT *
FROM student
WHERE (age = 18 AND grade = 1)
   OR (age = 23 AND grade = 2);
```

圆括号是必要的。

此查询将返回年龄为18且年级为1的学生，以及年龄为23且年级为2的学生。

## 二、MyBatis使用

* 一般配合`Set`数据类型使用，防止条件重复。

```java
public record DelStuDo(Integer age, Integer grade) {
}
```

* Mapper.java接口

```java
public interface StudentMapper extends BaseMapper<Student> {
    void deleteByAgeAndGrade(Set<DelStuDo> stuDoSet);
}
```

* Mapper.xml
* 使用`separator=" OR "`来对查询条件进行连接

```xml
<delete id="deleteByAgeAndGrade">
    DELETE Student
    WHERE
    <foreach collection="stuDoSet" item="item" separator=" OR ">
        (age = #{item.age} AND grade = #{item.grade})
    </foreach>
</delete>
```

