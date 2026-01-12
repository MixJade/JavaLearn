# MySql导出Insert文件

> 2026-01-12 17:55:04

## 一、mysqldump命令讲解

`mysqldump`是MySQL自带的命令行备份工具，无需额外安装，核心用于生成数据库/表的结构和数据脚本，专门生成`INSERT`语句（仅数据）的用法如下：

### 1. 核心命令格式（仅导出数据，生成INSERT语句）
```bash
# 基础格式（本地MySQL服务器）
mysqldump -u [用户名] -p[密码] -t [数据库名] > [保存的脚本文件路径].sql

# 示例（用户名root，数据库testdb，保存到D盘mysql_data.sql）
mysqldump -u root -p123456 -t testdb > D:\mysql_data.sql
```

### 2. 关键参数解释
- `-u [用户名]`：指定连接MySQL的用户名（如`root`）。
- `-p[密码]`：指定用户密码，**注意`-p`和密码之间无空格**（若省略密码直接写`-p`，执行后会提示交互式输入密码，更安全，避免密码暴露在命令历史中）。
- `-t`：**核心参数**，作用是「不生成表结构创建语句（`CREATE TABLE`），仅生成数据的`INSERT`语句」。
- `[数据库名]`：需要导出数据的目标数据库名称。
- `> [文件路径].sql`：将命令执行结果（`INSERT`语句）保存到指定的`sql`文件中。

### 3. 补充可选参数（优化INSERT脚本）
- `-B [数据库名]`：添加数据库名前缀（`INSERT INTO testdb.table_name ...`）。
- `--extended-insert=FALSE`：默认`mysqldump`会生成批量`INSERT`语句（一条语句插入多条数据），该参数设置为`FALSE`后，会为每条数据生成单独的`INSERT`语句，可读性更强（但导入效率较低）。
- 远程服务器连接：若需连接远程MySQL，添加`-h [服务器IP] -P [端口号]`（默认端口3306）

## 二、导出远程数据库的数据到本地

```bash
mysqldump -h [远程服务器IP/主机名] -P [远程MySQL端口] -u [远程数据库用户名] -p -t [远程数据库名] > [本地保存路径].sql
```

* 示例SQL（相关参数说明见上文）

```bash
mysqldump -h 192.168.1.100 -P 3306 -u root -p -t testdb > D:\mysql_data.sql
```

