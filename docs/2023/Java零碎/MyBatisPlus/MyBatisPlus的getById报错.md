# MyBatisPlus的getById报错

> 2024年6月28日15:13:19

问题描述：使用`MyBatisPlus`自带的`getById`方法报错，且因为我既没有启动后端代码的权限，也没有看服务器日志的权限，导致不知道怎么修改。

* 最终解决：
* 我在查询返回的实体类中加了字段，但没有打注解
* 在那个存在实体类中，但不存在数据库中的字段上，打上`@TableField(exist = false)`就好

```java
@Schema(description = "联查名称")
@TableField(exist = false)
private String dogsName;
```

