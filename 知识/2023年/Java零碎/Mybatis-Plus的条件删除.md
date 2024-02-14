# Mybatis-Plus的条件删除

这个删除的条件构造器用的是更新操作的，毕竟删除也算一种更新嘛

```java
groupRelService.lambdaUpdate()
    .eq(AuthUgroupRel::getUsrNo, userID)
    .remove();
```

