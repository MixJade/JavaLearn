# Oracle将小数转为百分号

* 下面的小数得保留小数点后四位

```sql
CONCAT( TO_CHAR( tb."投资比例" * 100, '990.99' ), '%' )
```



## 二、实际运用

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

