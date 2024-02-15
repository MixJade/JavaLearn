# Oracle新增字段

> 2024年2月27日16:18:46

* 在工作中，为数据库加字段是常有的事。
* 每次出现新需求，为了方便实现，总会在数据库中加上那么一两个字段。

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