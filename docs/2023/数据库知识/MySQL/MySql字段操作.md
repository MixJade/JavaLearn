# MySql字段操作

> 2025-11-27 09:57:14

## 加字段

```sql
ALTER TABLE 你的表名 
ADD COLUMN 字段名称 字段类型 COMMENT '注释';
```

* 示例

```sql
ALTER TABLE Dog 
ADD COLUMN master_name varchar(40) COMMENT '狗主人名称';
```

**注意**：如果mysql的数据库编码为`utf8mb4`，那么在varchar类型的计算中，一个汉字占3到4个字节，建议按4个字节计算