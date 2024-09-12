# Oracle数据库闪回

> 2024-09-12 09:27:33
>
> 参考博客：[Oracle数据库的闪回操作 博客园](https://www.cnblogs.com/muhy/p/11171976.html)

* 只能恢复通过`DELETE`语句删除的数据，不能恢复被`TRUNCATE TABLE`语句清空的表

通过DELETE删除数据后相当于放入回收站，一般情况下可以找回；

通过UPDATE更新数据后数据库也会保留数据快照。

闪回就是恢复指定时间的数据快照以达到恢复数据的目的。
根据步骤一查询出数据快照，恢复数据自行决定（之前的数据都有了，咋恢复还不会吗？）

## 一、查询指定时间的数据快照

- 1、查询执行过SQL语句，确定快照的时间

```sql
SELECT R.FIRST_LOAD_TIME,R.SQL_TEXT,R.* FROM V$SQLAREA R
WHERE R.SQL_TEXT LIKE '%ABOUT YOUR SQL%' ORDER BY R.FIRST_LOAD_TIME DESC
```

- 2、查询基于指定时间的数据快照

```sql
 SELECT * FROM YOUR_TABLENAME AS OF TIMESTAMP
  TO_TIMESTAMP('2019-02-05 20:00:00', 'yyyy-mm-dd hh24:mi:ss');
  --以当前时间为准，125分钟之前的数据快照
SELECT * FROM YOUR_TABLENAME AS OF TIMESTAMP SYSDATE - 125 / 1440 
```

## 二、恢复数据
`FLASHBACK`时，如果不确定删除的具体时间，在没有太多操作这个表的情况下，闪回的时间稍微提前一点。

闪回表数据SQL语句：

- 1、启动表的row movement特性

```sql
ALTER TABLE YOUR_TABLENAME ENABLE ROW MOVEMENT;
```

- 2、闪回指定时间的快照

```sql
FLASHBACK TABLE YOUR_TABLENAME TO TIMESTAMP
TO_TIMESTAMP('2018-04-23 16:06:00','yyyy-mm-dd hh24:mi:ss');
```

- 3、关闭表的row movement功能

```sql
ALTER TABLE YOUR_TABLENAME DISABLE ROW MOVEMENT;
```
