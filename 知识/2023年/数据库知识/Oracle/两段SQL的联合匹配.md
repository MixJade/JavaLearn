# 两段SQL的联合匹配

> 2024年3月15日16:05:07

## 一、问题背景

如何将这两段SQL进行融合成一个SQL?

* 查询【家】表对应的【爱狗联盟】下的狗

```sql
-- 查询2024031501地区【家】所对应的【爱狗联盟】下的狗
-- 但不查询弃用的、种类为1的狗
SELECT iDog.dogId, iDog.dogTp
FROM Home h1,
   IDogTeamRel iDog
WHERE areaId = '2024031501'
 AND h1.teamId = iDog.teamId
 AND NOT (iDog.dogTp = '1' AND iDog.useState = '0');
```

* 查询【家】表对应的【家与狗的关系】下的狗

```sql
-- 查询2024031501地区【家】中的狗
SELECT hd1.homeName, hd1.dogId, hd1.dogRole
FROM Home h1,
   HomeAndDog hd1
WHERE areaId = '2024031501'
 AND hd1.homeId = h1.homeId;
```

我希望第二段SQL中的`dogRole`、`dogId`等于第一段SQL的`dogTp`、`dogId`的数据才能被查出来。

## 二、融合结果

> 注意: 使用Join on来连接表，其查询效率比用and高

如果希望两个查询的结果匹配，可以使用子查询或`JOIN`。在这里，建议使用`INNER JOIN`，因为它会返回两边匹配的记录。

```sql
SELECT hd1.homeName, iDog.dogId, iDog.dogTp AS dogRole
FROM Home h1
JOIN IDogTeamRel iDog ON h1.teamId = iDog.teamId
JOIN HomeAndDog hd1 ON h1.homeId = hd1.homeId
WHERE h1.areaId = '2024031501'
AND NOT (iDog.dogTp = '1' AND iDog.useState = '0')
AND hd1.dogRole = iDog.dogTp
AND hd1.dogId = iDog.dogId;
```

上述查询首先根据`Home`和`IDogTeamRel`表的`teamId`的要求联接表，然后根据`Home`和`HomeAndDog`表的`homeId`进一步联接。然后，在`WHERE`子句中，设置了筛选条件，以匹配两个查询中的字段。最后，在选定的字段中选择了`homeName`、`dogId`、`dogTp`，并将其更名为`dogRole`。