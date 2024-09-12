# MyBatis在Oracle批量插入

> 2024-09-12 09:32:49

* 主要使用`INSERT ALL`语句
* 参考博客1：[Mybatis基于Oracle数据库实现批量插入-CSDN博客](https://blog.csdn.net/XikYu/article/details/134203099)
* 参考博客2：[Oracle中 insert all 语句-CSDN博客](https://blog.csdn.net/Ruishine/article/details/127440137)

主要实现：

```xml
<insert id="insertBatch">
    INSERT ALL
    <foreach collection="list" item="user" separator=" "
             close="SELECT * FROM dual" index="index">
        INTO LY_TEST (id, name, age)
        VALUES (#{user.id}, #{user.name}, #{user.age})
    </foreach>
</insert>
```

