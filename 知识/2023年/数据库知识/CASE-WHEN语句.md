# CASE-WHEN语句

> 2024年3月22日17:41:28

在Oracle和MySQL中，CASE WHEN语句可以用于在SQL查询中进行条件判断和值的选择。它的工作方式类似于`if-then-else`的逻辑。其基本结构如下：

```sql
CASE
  WHEN condition1 THEN result1
  WHEN condition2 THEN result2
  ...
  WHEN conditionN THEN resultN
  ELSE result
END;
```

下面是一个具体的例子，在用户表（user_table）中依据用户的年龄（age）给出不同的评价：

```sql
SELECT name,
CASE
  WHEN age < 13 THEN '少儿'
  WHEN age >= 13 AND age <= 19 THEN '青少年'
  WHEN age >= 20 AND age <= 59 THEN '成年'
  ELSE '老年'
END AS age_group
FROM user_table;
```

这个查询会为每一个用户基于他们的年龄返回一个`age_group`，如果他们的年龄小于13，会返回`少儿`；如果年龄在13到19之间，就返回`青少年`，如果在20到59之间，就返回`成年`，否则返回`老年`。

CASE WHEN语句也可以在UPDATE或者DELETE语句中使用，以满足更复杂的条件。