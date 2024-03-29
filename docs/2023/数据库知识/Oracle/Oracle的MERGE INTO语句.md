# Oracle的MERGE INTO语句

Oracle的MERGE INTO语句允许您将两个表的内容合并。 如果行存在于表中，则更新该行；如果行不存在，则插入一行。

语法为：
```sql
MERGE INTO target_table
USING source_table
ON (condition)
WHEN MATCHED THEN 
   UPDATE SET column1 = value1, ..., column_n = value_n
   WHERE clause
WHEN NOT MATCHED THEN
   INSERT (column1, ..., column_n)
   VALUES (value1, ..., value_n)
   WHERE clause;
```
在此语句中：

- `target_table`：要合并的目标表(必须存在于数据库)。
- `source_table`：源表，可以是一个SELECT语句。
- `ON condition`： 使用这个条件判断是否存在冲突的行，即存在于两个表中。
- `WHEN MATCHED THEN UPDATE...WHERE clause`： 如果条件匹配，则更新目标表中的行。 WHERE子句是可选的。
- `WHEN NOT MATCHED THEN INSERT...WHERE clause`：如果条件不匹配，则在目标表中插入新行。WHERE子句是可选的。

这个语句通常用于表的维护和更新，特别是在数据存储仓库中是非常有用的。
