# MyBatis报错无效列类型

> 2024年5月13日16:37

先说结论：是因为**所传的值中有null**，而MyBatis不能将`null`放入占位符(`#{xxx}`)中，因为MyBatis要推断传参的类型，而null显然是推断不出来的，所以报错。

## 一、网上抄的方法

> Caused by: org.apache.ibatis.type.TypeException: Error setting null for parameter #1 with JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: java.sql.SQLException: 无效的列类型: 1111
>     at org.apache.ibatis.type.BaseTypeHandler.setParameter(BaseTypeHandler.java:47)
>     at org.apache.ibatis.scripting.defaults.DefaultParameterHandler.setParameters(DefaultParameterHandler.java:87)
>     ... 48 more
> Caused by: java.sql.SQLException: 无效的列类型: 1111

指定插入值的jdbcType，将xml方法的sql改成:

```sql
insert into user(id,name) values({id,jdbcType=VARCHAR},#{name,jdbcType=VARCHAR})
```

或者xml文件判空。

## 二、提前设置默认值

比如在最终修改数据库之前检查一遍可能会传的值

* 必须传的值，强制判空，为Null就返回报错信息。
* 可能传，但又不必须的值，则检查时为空则设置一个默认值。