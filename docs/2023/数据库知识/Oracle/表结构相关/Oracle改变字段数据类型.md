# Oracle改变字段数据类型

> 2024年6月11日17:02:36

* 比如将一个字段的数据类型改为`NVARCHAR2(40)`

```sql
ALTER TABLE table_name
MODIFY column_name NVARCHAR2(40);
```

