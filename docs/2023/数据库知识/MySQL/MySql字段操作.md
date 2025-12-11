# MySql字段操作

> 2025-11-27 09:57:14

## 加字段

```sql
alter table 你的表名 
    add column 字段名称 字段类型 comment '注释';
```

* 示例

```sql
alter table dog 
    add column master_name varchar(40) comment '狗主人名称';
```

**注意**：如果mysql的数据库编码为`utf8mb4`，那么在varchar类型的计算中，一个汉字占3到4个字节，建议按4个字节计算

## 加字段时指定位置

mysql在加字段时，可指定字段的位置

比如在`dog_name`后面加`master_name`字段

```sql
alter table dog 
	add column master_name varchar(40) comment '狗主人名称' after dog_name
```

