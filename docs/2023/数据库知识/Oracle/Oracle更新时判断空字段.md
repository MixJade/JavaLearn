# Oracle更新时判断空字段

> 2024年4月23日16:30:09

* Oracle在更新前做判断：如果目标的表的某个字段为空，则将该空字段一同更新

```sql
UPDATE tablename
SET fieldname1 = 'newvalue',
    fieldname2 = (CASE WHEN fieldname2 IS NULL THEN 'newvalue' ELSE fieldname2 END)
WHERE some_other_field = 'something';
```

* 在这个例子中，如果fieldname2为空，那么字段fieldname1和fieldname2都会被新的值替代。
* 如果fieldname2不为空，那么fieldname2都将保持原样。同时fieldname1正常更新