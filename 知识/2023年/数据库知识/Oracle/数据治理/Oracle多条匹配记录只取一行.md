# Oracle多条匹配记录只取一行

> 2024年3月5日14:40:46

以下SQL本意是通过`Dog_and_Team`中的`DogId`来更改`Dog`的`teamID`字段，但`Dog_and_Team`中`dogID`有多个，导致报错：

```text
ORA-01427:单行子查询返回多个行
```

于是加入逻辑：(如果有`Dog_and_Team`中，有多个dogID，则匹配创建时间最早的那个)

## 一、对数据进行分组，只取每组第一个

`Dog_and_Team`表中对应的`dogID`有多条记录，需先通过`dogID`进行分组，并判断每组中，哪一条`createTime`最早，然后更新到另一张表。

```sql
UPDATE Dog d1
SET teamID = (
    SELECT teamID 
    FROM (
        SELECT teamID, ROW_NUMBER() OVER (PARTITION BY dogID ORDER BY createTime) rn
        FROM Dog_and_Team 
        WHERE dogID = d1.dogID
    )
    WHERE rn = 1
);
```

上面的这个Oracle SQL使用`ROW_NUMBER()`函数为每个`dogID`的数据分配一个行号`rn`，最早的`createTime`的行号为1。在更新的时候只选择行号为1的记录更新到`Dog`表。这个方法保证了对于每个`dogID`，只获取`createTime`最早的那个`teamID`。

## 二、PARTITION BY 语句的介绍

具体来说，`ROW_NUMBER() OVER (PARTITION BY dogID ORDER BY createTime)`这句的使用教程如下：

- `ROW_NUMBER()`：是窗口函数，用于生成一组唯一的行号。

- `OVER`：用于指定窗口函数的排序和分组。

- `PARTITION BY dogID`：将数据划分成若干分组，每个分组有一个唯一的`dogID`。

- `ORDER BY createTime`：在每个`dogID`内，根据`createTime`对数据进行排序。

所以，这段SQL的全面解释是：在`Dog_and_Team`表中，对于每个`dogID`，都会生成一个行数字段`rn`，该字段代表了在相同的`dogID`内，根据`createTime`排序的顺序。