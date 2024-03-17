# 对UNION连接的结果进行排序

> 2024年3月22日17:35:14

在多个UNION连接的结果中进行排序，需要在查询的最后使用 ORDER BY 子句。UNION 操作符将多个 SELECT 语句的结果组合成一个结果集，这个结果集可以被 ORDER BY 子句排序。以下是一个例子：

```sql
SELECT column_name(s) FROM table1
UNION
SELECT column_name(s) FROM table2
ORDER BY column_name(s);
```
注意，ORDER BY 子句必须放在最后一个SELECT语句之后，它将按照顺序对整个结果集进行排序，而不仅仅是最后一个SELECT语句返回的结果。如果需要对每个SELECT语句返回的结果分别排序，那么就需要对每个SELECT语句单独使用ORDER BY。