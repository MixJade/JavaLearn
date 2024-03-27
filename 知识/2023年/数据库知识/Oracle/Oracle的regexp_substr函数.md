# Oracle的regexp_substr函数

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