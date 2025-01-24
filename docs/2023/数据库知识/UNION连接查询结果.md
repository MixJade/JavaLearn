# UNION连接查询结果

> 2024年3月22日17:35:14

## 一、union和union all区别

* union：对两个结果集进行并集操作，不包括重复行，同时进行默认规则的排序；
* union All：对两个结果集进行并集操作，包括重复行，不进行排序

## 二、样例

* union为连接两个结果列之后进行去除与排序。
* union all 为连接两个结果列，包括了重复数据。

```sql
SELECT client_photo FROM client WHERE client_photo IS NOT NULL AND client_photo != ''
UNION
SELECT doctor_photo FROM doctor WHERE doctor_photo IS NOT NULL AND doctor_photo != ''
UNION
SELECT pet_photo FROM pet WHERE pet_photo IS NOT NULL AND pet_photo != ''
UNION
SELECT employee_photo FROM employee WHERE employee_photo IS NOT NULL AND employee_photo != ''
```

## 三、对连接结果进行排序

在多个UNION连接的结果中进行排序，需要在查询的最后使用 ORDER BY 子句。UNION 操作符将多个 SELECT 语句的结果组合成一个结果集，这个结果集可以被 ORDER BY 子句排序。以下是一个例子：

```sql
SELECT column_name(s) FROM table1
UNION
SELECT column_name(s) FROM table2
ORDER BY column_name(s);
```
注意，ORDER BY 子句必须放在最后一个SELECT语句之后，它将按照顺序对整个结果集进行排序，而不仅仅是最后一个SELECT语句返回的结果。如果需要对每个SELECT语句返回的结果分别排序，那么就需要对每个SELECT语句单独使用ORDER BY。

## 四、关于UNION去重

`UNION` 操作符会自动去重，也就是说它会去除查询结果中的重复行。如有两个查询 `a` 和 `b`，那么 `UNION` 会去掉重复的行，包括 `b` 中的重复行，即使这些重复行在 `a` 中不存在。

比如有两个查询A和B，A返回结果1,2，而B返回结果3,3。当使用UNION来连接这两个查询时，结果将会是1,2,3