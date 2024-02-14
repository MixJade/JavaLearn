# Oracle的部分语法

## 一、类似GROUP_CONCAT()的

* Mysql有GROUP_CONCAT()，可以将两行合成一行，中间用逗号分割。Oracle有类似的，但语法更繁琐。
* 语法如下(可以指定分隔符)：

```sql
SELECT A.name, LISTAGG(B.address, ', ') WITHIN GROUP (ORDER BY B.address) as addresses
FROM A
LEFT JOIN B ON A.id = B.A_id
GROUP BY A.name;
```

* 注意：在Oracle中，后面的`GROUP BY`需要将所有的在查询中，但不在分组中的列写上。 

## 二、将小数转为百分号

* 下面的小数得保留小数点后四位

```sql
CONCAT( TO_CHAR( tb."投资比例" * 100, '990.99' ), '%' )
```

## 三、去重语句

在MySQL中的去重是这样的，用的是`UNIQUE`：

```sql
SELECT UNIQUE column1, column2, ..., columnN 
FROM table_name;
```

而在Oracle中不一样，用的是`DISTINCT`：

```sql
SELECT DISTINCT column1, column2, ..., columnN 
FROM table_name;
```

## 四、实际运用

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

