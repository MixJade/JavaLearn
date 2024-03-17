# Oracle对两段SQL取交集

> 2024年3月22日15:27:44

在Oracle中，可以使用`INTERSECT`运算符来获取两个SQL查询的交集。

```sql
SELECT column_name(s) FROM table1
INTERSECT
SELECT column_name(s) FROM table2;
```
这里第一行查询会从`table1`中选取数据，第二行查询从`table2`中选取数据，并返回两个结果集的共有部分。 

需要注意的是，`INTERSECT`运算符会剔除结果集中的重复数据。 

如果要查找的列存在于多个表中，需要确保两个`SELECT`中的列的数据类型是匹配的，也就是说，如果`table1`中的`column_name`列是数字类型，那么`table2`中的`column_name`列也需要是数字类型。