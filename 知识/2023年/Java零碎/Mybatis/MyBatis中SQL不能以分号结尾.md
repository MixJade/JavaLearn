# MyBatis中SQL不能以分号结尾

> 2024年3月15日18:36:49

* 如果在xml中的sql结尾加了分号，在Oracle中，会导致报错。

```xml
<select id="queryDogBydogID" resultType="com.demo.entity.Dog">
    SELECT dogName
    FROM Dog
    WHERE dogID = #{dogId};
</select>
```

* 会报如下错误

```text
Caused by: oracle.jdbc.OracleDatabaseException: ORA-00933: SQL 命令未正确结束
```

* 事实上只有Oracle会这样，MySQL不会。

