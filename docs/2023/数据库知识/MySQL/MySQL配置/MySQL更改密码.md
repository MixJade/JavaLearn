# MySQL更改密码

> 2024年4月7日14:10:34

## 登录并查看版本

首先，打开命令行界面并登录MySQL。 （需输入密码登录）

```bash
mysql -u root -p
```

如果没有进入mysql管理界面，可能mysql服务没有开启，可以通过命令：`net start mysql`开启。

然后查看MySQL版本

```sql
select version();
```

登录成功后，根据MySQL版本不同，输入不同命令更改密码

## 5.7版本以下

如果你的MySQL版本是5.7以下, 可使用以下命令:
```sql
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('新密码');
```

## 5.7版本以上

如果你的MySQL版本是5.7及以上, 可使用以下命令:

```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY '新密码';
```

* 或者（在用户登录时不需要任何SSL或X509证书）

```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY '新密码' REQUIRE NONE;
```

## 刷新与退出

记得每执行完一条mysql命令后面都要加上分号。

以上命令执行成功后，使用以下命令使更改的密码生效：
```bash
FLUSH PRIVILEGES;
```

然后退出MySQL：
```bash
exit
```

注意：以上步骤如果在执行时报错，可能是因为你的账户没有权限，解决的方法是用超级管理员身份运行命令行工具。