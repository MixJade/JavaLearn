# Oracle字段操作

> 2024年7月24日17:55:30

## 一、新增字段

> 2024年2月27日16:18:46

* 在工作中，为数据库加字段是常有的事。
* 每次出现新需求，为了方便实现，总会在数据库中加上那么一两个字段。

### 1.1 SQL语法

```sql
-- 正常的加入字段
ALTER TABLE 待添加字段的表
    ADD (所添加字段 VARCHAR2(40 BYTE) VISIBLE);

COMMENT ON COLUMN 待添加字段的表.所添加字段 IS '新的字段注释';

-- 为字段设置一些属性
ALTER TABLE 待添加字段的表
    ADD IS_OK NUMBER(1, 0) DEFAULT 0 NOT NULL;

COMMENT ON COLUMN 待添加字段的表.IS_OK IS '这好吗';

-- 将已有字段设为主键
ALTER TABLE 待添加字段的表
    ADD PRIMARY KEY (所添加字段);
```

### 1.2 VISIBLE属性说明

Oracle中的“visible”用于指定列的可见性。如果一个列被标记为VISIBLE，那么在查询表时，这个列的数据会被返回。反之，如果一个列被标记为INVISIBLE，那么在查询表时，这个列的数据不会被返回，除非明确地在查询中指定列名。

例如，当你有一列存储敏感数据，如身份证号或者电话号码，你可能不想这些信息在普通的SELECT查询中出现，即使它们仍然存在于表中。在这种情况下，你可以将该列标记为INVISIBLE。

请注意，此功能只有在Oracle 12c及更高版本中才可用。在早期的版本中，不支持INVISIBLE列。

```sql
ALTER TABLE "待添加字段的表" ADD ("所添加字段" VARCHAR2(40 BYTE) VISIBLE );

COMMENT ON COLUMN "待添加字段的表"."所添加字段" IS '新的字段注释';
```

### 1.3 关于列名已存在

> 可以放行执行脚本，而不必担心重复添加。

Oracle数据库的同一张表不能有两个名称相同的列，这是数据库设计的基本原则之一。每个列都应该有其唯一的列名，以便在查询、修改或引用数据时能够明确指定特定的列。

如果尝试添加一个名称与已存在的列相同的列，Oracle将会报错，提示已存在具有相同名称的列。

## 二、改变字段数据类型

> 2024年6月11日17:02:36

* 比如将一个字段的数据类型改为`NVARCHAR2(40)`

```sql
ALTER TABLE table_name
MODIFY column_name NVARCHAR2(40);
```

## 三、删除列与更改列名

> 2024年7月24日16:37:46

* 删除旧列：

```sql
ALTER TABLE your_table DROP COLUMN your_column;
```

* 把新列名字改回旧列的名字：

```sql
ALTER TABLE your_table RENAME COLUMN new_column TO your_column;
```

## 四、查询某列是否存在

> 2025-05-23 09:29:55

* Oracle 默认将对象名（表名、列名）存储为大写，因此条件中需使用大写形式

```sql
SELECT COUNT(*)
FROM USER_TAB_COLUMNS
WHERE TABLE_NAME = '表名' -- 替换为实际表名（大写）
  AND COLUMN_NAME = '列名'; -- 替换为实际列名（大写）
  
-- 如果结果为1，表示存在该列；结果为0则表示不存在
```

