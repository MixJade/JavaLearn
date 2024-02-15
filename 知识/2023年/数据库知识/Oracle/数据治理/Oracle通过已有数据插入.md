# Oracle通过已有数据插入

> 2024年3月7日20:36:37

* 这里的`2024030717202201`是提前生成的时间戳
* 这里是`SYSDATE`是Oracle的时间函数，用于生成当前时间

```sql
INSERT INTO Dog (dogID, homeID,homeNM, dogTP, dogNM, CREATE_TIME)
SELECT '2024030717202201',ho1.homeID,ho1.homeNM,'2','旺财' ,SYSDATE
FROM Home ho1
WHERE ho1.homeID = '1021' AND ho1.homeTP='2';
```

