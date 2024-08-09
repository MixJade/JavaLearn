# MySQL8连接报错

> 2024-08-09 15:13:34

## Public Key Retrieval is not allowed

使用一些数据库管理工具链接MySQL8的时候,会有报错：

```text
Public Key Retrieval is not allowed
```

mysql 8.0 默认使用 caching_sha2_password 身份验证机制 （即从原来mysql_native_password 更改为 caching_sha2_password。）

从 5.7 升级 8.0 版本的不会改变现有用户的身份验证方法，但新用户会默认使用新的 caching_sha2_password 。 客户端不支持新的加密方式。 修改用户的密码和加密方式。

### 解决方法

在数据库链接后面加上`allowPublicKeyRetrieval=TRUE`即可

* 比如下面这样

```properties
url=jdbc:mysql://localhost:3306/play?allowPublicKeyRetrieval=TRUE
```

