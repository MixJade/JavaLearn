# MySQL删除重复数据

```sql
-- 删除mysql中重复数据:
delete a from students a,students b where a.学号 = b.学号 and a.id > b.id;
```

