# Oracle删除列与更改列名

> 2024年7月24日16:37:46

* 删除旧列：

```sql
ALTER TABLE your_table DROP COLUMN your_column;
```

* 把新列名字改回旧列的名字：

```sql
ALTER TABLE your_table RENAME COLUMN new_column TO your_column;
```