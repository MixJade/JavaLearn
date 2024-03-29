# Oracle查询语法合集

> 2024年3月29日16:12:51

[TOC]

---

## 一、去重语句DISTINCT

Oracle中的去重与MySQL一样，用的是`DISTINCT`：

```sql
SELECT DISTINCT column1, column2, ..., columnN 
FROM table_name;
```

---

## 二、INTERSECT取交集

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

---

## 三、小数转为百分号

* 下面的小数得保留小数点后四位

```sql
CONCAT( TO_CHAR( tb."投资比例" * 100, '990.99' ), '%' )
```

**实际运用**

> 请参考：
>
> * Oracle去重语句DISTINCT
> * Oracle聚合语法LISTAGG

注意：`表B`（投资产品及比例）中保存了投资的产品名字`("产品名字")`、投资的比例`("投资比例")`，与`表A`（投资策略表）是多对一的关系

```sql
SELECT
	ta."策略名称",
--下面是Oracle的GROUP_CONCAT()
	LISTAGG ( tb."产品名字", '、' ) WITHIN GROUP ( ORDER BY tb."投资比例" DESC ) AS "产品类别",
-- 下面是将查询的产品名称与对应的百分号拼接起来
	LISTAGG ( CONCAT( tb."产品名字", CONCAT( TO_CHAR( tb."投资比例" * 100, '990.99' ), '%' )), '; ' ) WITHIN GROUP ( ORDER BY tb."投资比例" DESC ) AS "投资比例展示"
FROM
	表A ta
	LEFT JOIN  表B tb ON tb."产品ID" = ta."产品ID" 
WHERE
	ta.USE_STATE = '1' 
	GROUP BY-- 在Select中，且不是LISTAGG的列要全部写上
	ta."策略名称"
```

查询结果：

|"策略名称"|"产品类别"|"投资比例展示"|
|:---:|:---:|:---:|
|策略A|产品甲、产品乙、产品丁|产品甲  55.56%; 产品乙  43.22%; 产品丁  1.22%|

---

## 四、connect by prior层次查询

> 2023年12月28日 9:45:31


### 4.1、查询当前部门的所有下级部门

"connect by prior DEPT_ID = SUPER_DEPT_ID"是Oracle数据库中的一种层次查询语句。

这里的"DEPT_ID"和"SUPER_DEPT_ID"表示部门的ID和该部门上级部门的ID。

"connect by prior"是连接之前的记录，建立层次关系。"prior"关键字表示上一级的记录。

这条语句的意思就是，将那些上级部门ID（SUPER_DEPT_ID）与部门ID（DEPT_ID）相等的行（也就是上级部门和该部门）连接起来，以此形成一个部门与上级部门之间的层次结构。

使用这种查询方式，可以很容易地得到一种树形结构的结果，如公司的部门结构，员工的管理层次等。

```sql
select DEPT_ID,
       DEPT_NM,
       SUPER_DEPT_ID
from MY_DEPT_INFO start with DEPT_ID = '12'
connect by prior DEPT_ID = SUPER_DEPT_ID
```

这句SQL中，DEPT_ID是当前部门号，SUPER_DEPT_ID是上级部门号，这句SQL的意思是查询当前部门的所有下级部门。


### 4.2、查询当前部门的上级部门

我希望能够翻转一下，变成查出当前部门的所有上级部门。

可以把"connect by prior DEPT_ID = SUPER_DEPT_ID"这句语句换一下位置，变成"connect by prior SUPER_DEPT_ID = DEPT_ID"，这样就可以实现查询当前部门的所有上级部门。

完整SQL语句如下：

```sql
select DEPT_ID,
       DEPT_NM,
       SUPER_DEPT_ID
from MY_DEPT_INFO start with DEPT_ID = '12'
connect by prior SUPER_DEPT_ID = DEPT_ID
```

这样就可以查询到部门号为'12'的所有上级部门信息了，按照层级向上查询。

---

## 五、ESCAPE转义关键字

在Oracle中，ESCAPE关键字通常和LIKE操作符一起使用，用于对特殊字符进行转义处理。当你需要查询的数据中含有通配符，比如百分比（%）和下划线（_）时，就可以使用ESCAPE关键字。

LIKE操作符的通配符%表示任意多个任意字符，_表示一个任意字符。假设有一个字段中包含了%或者_，如果你直接做此字段like '%'或者'_'，那么返回的结果就不对。

所以，要想正确查询此类名称，就需要在查询条件字符串中加ESCAPE对特殊字符做转义处理。

例如，假设你要查找包含百分比（%）的数据，你可以这样写SQL查询：

```sql
SELECT * 
FROM table_name 
WHERE column_name LIKE '%\%%' ESCAPE '\';
```

在上面的例子中，ESCAPE后面的'\'就是一个转义字符，告诉数据库'\%'这个字符只是一个普通的百分比字符，不再是通配符。

类似地，对于下划线（_）的转义处理，也可以这样写：

```sql
SELECT * 
FROM table_name 
WHERE column_name LIKE '%\_%' ESCAPE '\';
```

这样，'\_' 这个字符只是一个普通的下划线字符，不再是通配符。

以上，就是ESCAPE关键字在Oracle中的一些常见应用。

---

## 六、NOT EXISTS语句

Oracle中的NOT EXISTS用于执行子查询，如果子查询没有返回任任行，则返回TRUE。它通常用于对某些填充条件进行过滤。

在SQL中，您可以使用子查询创建一个临时表，您可以使用NOT EXISTS，EXISTS或JOIN这个临时表来过滤或者提取出需要的数据。

这是一些例子：

例1：获取在“orders”表中但不在“order_details”表中的所有订单。

```sql
SELECT order_id 
FROM orders
WHERE NOT EXISTS (
    SELECT 1 
    FROM order_details 
    WHERE order_details.order_id = orders.order_id
)
```
这个查询返回的是所有在"orders"表中但没有任何详细信息在"order_details"表中的订单。

例2：获取在“employees”表中，但是不在“sales”表中的所有员工。

```sql
SELECT employee_id 
FROM employees
WHERE NOT EXISTS (
    SELECT 1 
    FROM sales 
    WHERE sales.employee_id = employees.employee_id
)
```
这个查询返回的是所有在"employees"表中但没有在"sales"表中出现过的员工。

---

## 七、regexp_substr函数

> 2024年3月27日14:32:19

在Oracle中，如果您想从一个以逗号分割的字段中查询数据，可以使用Oracle的内置函数REGEXP_SUBSTR。下面是一个具体的例子：

首先，假设你的表名是`your_table`，字段名是`your_field`，且其中一行的值是`2,3,5,6`。那么你可以使用如下的代码：

```sql
SELECT REGEXP_SUBSTR (your_field, '[^,]+', 1, level) as your_field
FROM your_table
CONNECT BY REGEXP_SUBSTR (your_field, '[^,]+', 1, level) IS NOT NULL;
```

这个代码会将`your_field`字段按照`,`来分割，并将分割后的每一部分作为单独的一行。代码中的`[^,]+`是一个正则表达式，它会匹配所有不包含`,`的字符串。CONNECT BY句是用来实现层次查询的，它会在每一行上反复执行REGEXP_SUBSTR函数，直到函数返回NULL为止。

- your_field包含的信息可能多个,逗号分割
- (解说:这里使用regexp_substr对逗号的分割进行提取,通过level参数决定提取第几个)
- (解说:这里使用CONNECT BY这个层次查询，来进行SQL递归，通过递归来不断设置level的值(level从1开始))
- (注意:这种会一直循环处理，如果数据量大，会导致耗时特别长。如有可能，建临时表导出csv，用python处理完再导回去)

---

## 八、聚合语法LISTAGG


### 8.1、有其它列的时候

* Mysql有GROUP_CONCAT()，可以将两行合成一行，中间用逗号分割。Oracle有类似的，但语法更繁琐。
* 语法如下(可以指定分隔符)：

```sql
SELECT A.name, LISTAGG(B.address, ', ') WITHIN GROUP (ORDER BY B.address) as addresses
FROM A
LEFT JOIN B ON A.id = B.A_id
GROUP BY A.name;
```

* 注意：在Oracle中，后面的`GROUP BY`需要将所有的在查询中，但不在分组中的列写上。 


### 8.2、没有其它列的时候

> 单纯的聚合结果罢了

```sql
SELECT LISTAGG(B.address, ', ') FROM  B 
```

