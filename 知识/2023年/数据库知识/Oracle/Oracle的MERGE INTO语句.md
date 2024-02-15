# Oracle的MERGE INTO语句

## 1.请教我Oracle的merge INTO语句

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

- target_table：要合并的目标表。
- source_table：源表，可以是一个选择语句。
- ON condition： 使用这个条件判断是否存在冲突的行，即存在于两个表中。
- WHEN MATCHED THEN UPDATE...WHERE clause： 如果条件匹配，则更新目标表中的行。 WHERE子句是可选的。
- WHEN NOT MATCHED THEN INSERT...WHERE clause：如果条件不匹配，则在目标表中插入新行。WHERE子句是可选的。

这个语句通常用于表的维护和更新，特别是在数据存储仓库中是非常有用的。

## 2.target_table能是通过Select语句查出来的吗

> 但source_table能

不好意思，这是不可能的。

在Oracle中，MERGE INTO 语句的目标表不能是一个 SELECT 语句的结果。目标表必须是一个实际存在的表。

你可以根据SELECT语句的结果先创建一个临时表，然后再用这个临时表作为MERGE INTO语句的目标表。或者，你可以修改你的逻辑，尝试直接将数据插入或更新到实际的表中。

## 3.oracle怎么给select出来的数据建表

你可以通过使用`CREATE TABLE AS SELECT`语句从现有表的查询结果中创建新表。下面是一个语法示例：

```sql
CREATE TABLE new_table AS 
SELECT column1, column2 
FROM existing_table 
WHERE condition;
```

在这段SQL语句中，新的表"new_table"会被创建，它将包含来自"existing_table"的"column1"和"column2"的所有行，前提是它们满足"where"条件。

这里还需要注意以下几点：
1. 新创建的表只包含那些在SELECT语句中列出的数据和字段。
2. 新表不会继承现有表的任何其他属性，如索引、主键约束等。
3. 如果指定的新表名已经存在，Oracle将返回一个错误。如果你希望在表存在的情况下替换它，可以使用`CREATE OR REPLACE TABLE`命令。

最后，确保你在执行这样的操作时具有足够的权限和考虑到数据库空间的使用情况。