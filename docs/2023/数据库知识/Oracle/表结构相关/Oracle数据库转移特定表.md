# Oracle数据库转移特定表

## 一、查询表名

* Oracle通过SQL查出数据库中自己所有的表名及表注释

```sql
SELECT a.table_name, b.comments
FROM user_tables a,
     user_tab_comments b
WHERE a.table_name = b.table_name
ORDER BY a.table_name
```

## 二、查询字段注释

* Oracle查出数据库中指定表名的表的字段注释，比如查出`“comment on column 你的表名.CREATE_TIME is '录入时间'”`这种
* 使用 `chr` 函数插入一个换行符（ASCII 值为 10）。
* 使用` LISTAGG()`来将多行一列的所有查询结果合并成一个

```sql
SELECT LISTAGG('comment on column ' || TABLE_NAME || '.' || COLUMN_NAME || ' is ''' || COMMENTS || ''';', chr(10))
FROM USER_COL_COMMENTS
WHERE TABLE_NAME = '你的表名'  -- 这里的等于可以换成IN，但没必要
  AND COMMENTS IS NOT NULL
```

## 三、查询表的DDL

> 但这个很鸡肋

* 执行完一次以后，再单独执行二句话也不会带上数据库名了

```sql
BEGIN
    -- 不带数据库名称
    DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM, 'EMIT_SCHEMA', false);
    --关闭存储属性
    DBMS_METADATA.set_transform_param(DBMS_METADATA.session_transform, 'STORAGE', FALSE);
    --关闭表空间属性
    DBMS_METADATA.set_transform_param(DBMS_METADATA.session_transform, 'TABLESPACE', FALSE);
END;
/

SELECT DBMS_METADATA.GET_DDL('TABLE', '你的表名', '你的数据库名')
FROM dual;
```

## 四、查询表的注释

在Oracle中，你可以使用以下SQL查询：

```sql
SELECT 'comment on table '||table_name||' is ''' ||comments|| ''';'
FROM all_tab_comments
WHERE table_name = '你的表名' AND owner='你的数据库名';
```

请记住，替换 '你的表名' 和 '你的schema名' 为你实际查找的表名和schema名。 此查询将返回数据库表的注释，如果没有注释，那么返回的结果将是空的。

## 五、综合查询

将所有结果混合在一起，记住：里面还带着数据库的名字，记得手动去掉

```sql
SELECT (
           -- 查询表的DDL
           SELECT DBMS_METADATA.GET_DDL('TABLE', '你的表名', '你的数据库名') FROM dual)
           ||';'|| (chr(10) || chr(10))
           -- 查询表的添加注释语句
           || (SELECT 'comment on table ' || table_name || ' is ''' || comments || ''';'
               FROM all_tab_comments
               WHERE table_name = '你的表名'
                 AND owner = '你的数据库名')
           || (chr(10) || chr(10))
           -- 查询表字段的添加注释语句
           || (SELECT LISTAGG('comment on column ' || TABLE_NAME || '.' || COLUMN_NAME || ' is ''' || COMMENTS || ''';',
                              chr(10))
               FROM USER_COL_COMMENTS
               WHERE TABLE_NAME = '你的表名'
                 AND COMMENTS IS NOT NULL)
FROM dual
```

## 六、删除表语句

```sql
drop table 你的表名;
```

## 七、CSV小知识

excel对csv筛选两列：

1. 选中行，按【Ctrl+Shift+L】快捷键，建立**筛选**格式；
2.  设置筛选第一列

## 八、正确的导出方法

首先，上面的导出有个问题，就是导出的时候带着表的表空间语句，表名前面带着数据库名称，且表名是双引号（生产端的数据库是老古董，有双引号就报错），以下是正确的方法：


* 首先需要使用IDEA的数据库功能。
* 打开数据库，打开“表”选项，鼠标右键（打开“表”的右键菜单）
* 选择“SQL脚本”-“SQL生成器”，就可以看到建表语句
* 然后勾选下面的使用分号作为语句分隔符
* 接着在左边的文件输出选项选择路径（最好新建一个文件夹），和下拉框第一项

注意事项：
- 注意1：IDEA的导出只能导出数据库中的所有表，导出完成以后记得筛选一下。
- 注意2：IDEA的导出DDL语句默认以斜杠【`/`】作为分隔符（老古董数据库不支持），记得调成分号。