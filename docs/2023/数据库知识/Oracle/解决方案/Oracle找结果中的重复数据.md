# Oracle找结果中的重复数据

> 2024年3月25日21:06:54

可以使用`GROUP BY`和`HAVING`关语句来找出结果中重复的数据。下面是一个实际的例子。

比如你有一个表`employees`，你想要找出工资（salary）重复的员工信息。你可以写SQL语句如下：

```sql
SELECT salary, COUNT(*) 
FROM employees 
GROUP BY salary 
HAVING COUNT(*) > 1;
```

这里，`GROUP BY`语句将结果按照salary（工资）字段进行分组，`HAVING`语句则过滤掉那些只有一个员工的工资（也就是非重复的工资）。最后查询的结果就是那些有重复的工资以及它们的数量。

但请注意，只因为工资重复，并不能说明员工是同一人，若需要找出相同员工，需要按更多的字段进行分组。