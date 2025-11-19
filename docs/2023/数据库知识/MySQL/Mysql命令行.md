# Mysql命令行

> 2025-05-23 20:52:31

* 打开`MySQL 8.1 Command Line Client`
* 输入密码就行，注意在这个黑框中，右键是粘贴，很方便

## 常用命令

### 运行sql脚本

* 可直接将sql脚本拖进去以输入路径

```sql
source 文件路径/文件名.sql;
```

### 查看数据库

```sql
show databases;
```

### 选中数据库

```sql
use 数据库名称
```

### 查看表

```sql
show tables;
```

## 调节字符编码

* 查看当前编码

```sql
-- 查看客户端、连接、结果集的编码（最影响交互的3个）
SHOW VARIABLES LIKE 'character_set_%';
-- 或更精准筛选
SHOW VARIABLES WHERE Variable_name IN ('character_set_client', 'character_set_connection', 'character_set_results');
```

* 将本次会话的编码更改为utf8

```sql
-- 临时生效（仅当前命令行会话，退出后失效）
SET NAMES utf8mb4;
-- 等价于同时设置3个变量：
-- SET character_set_client = utf8mb4;
-- SET character_set_connection = utf8mb4;
-- SET character_set_results = utf8mb4;
```

