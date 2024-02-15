# Oracle聚合语法LISTAGG

## 一、有其它列的时候

* Mysql有GROUP_CONCAT()，可以将两行合成一行，中间用逗号分割。Oracle有类似的，但语法更繁琐。
* 语法如下(可以指定分隔符)：

```sql
SELECT A.name, LISTAGG(B.address, ', ') WITHIN GROUP (ORDER BY B.address) as addresses
FROM A
LEFT JOIN B ON A.id = B.A_id
GROUP BY A.name;
```

* 注意：在Oracle中，后面的`GROUP BY`需要将所有的在查询中，但不在分组中的列写上。 

## 二、没有其它列的时候

> 单纯的聚合结果罢了

```sql
SELECT LISTAGG(B.address, ', ') FROM  B 
```

