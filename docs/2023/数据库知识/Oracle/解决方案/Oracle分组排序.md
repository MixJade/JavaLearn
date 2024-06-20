# Oracle分组排序

> 2024年6月20日20:28:20

## 一、分组排序

* 将一张表中的数据，按照某一字段进行分组，
* 并在每个分组中，按照某个字段的值进行排序

```sql
-- 查询节点顺序
SELECT Employee_ID,
       Employee_NM,
       Dept_ID,
       ROW_NUMBER() OVER (PARTITION BY Dept_ID ORDER BY Employee_ID) as rank_order
FROM Employee
WHERE Is_del = '0'
  AND (Employee_TP = '1' OR Employee_TP = '3');
```

## 二、通过分组排序更新表

* 排完序当然需要更新回去
* 使用`MERGE INTO`语法更新

```sql
-- 变更节点顺序
MERGE INTO Employee emp
USING (SELECT Employee_ID,
              Employee_NM,
              Dept_ID,
              ROW_NUMBER() OVER (PARTITION BY Dept_ID ORDER BY Employee_ID) as rank_order
       FROM Employee
       WHERE Is_del = '0'
         AND (Employee_TP = '1' OR Employee_TP = '3')) ST
ON (ST.Employee_ID = emp.Employee_ID)
WHEN MATCHED THEN
    UPDATE
    SET emp.Rank = ST.rank_order
    WHERE emp.Rank IS NULL;
```

## 三、(补充)通过多个数据分组

只需要在分组条件后面通过*双竖线*拼接一个新条件即可

```sql
-- 查询实例节点顺序
SELECT Employee_ID,
       Employee_NM,
       Dept_ID,
       Head_ID,
       ROW_NUMBER() OVER (PARTITION BY Dept_ID||Head_ID ORDER BY Employee_ID) as rank_order
FROM Employee
WHERE Is_del = '1'
  AND (Employee_TP = '1' OR Employee_TP = '3');
```

