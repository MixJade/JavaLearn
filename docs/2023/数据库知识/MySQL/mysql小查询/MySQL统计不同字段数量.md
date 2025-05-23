# MySQL统计不同字段数量

> 2024年3月23日22:49:46

你可以使用`GROUP BY`来完成。假设你有名为`employment`，包含状态字段`status`的表，你可以如下查询：

```sql
SELECT status, COUNT(*) 
FROM employment 
GROUP BY status;
```
这个查询返回每种状态的人数。`GROUP BY`将`status`字段相同的行分到一组，然后`COUNT(*)`计算每组的数量。

例如，如果你的表如下：

| id | status |
|----|--------|
| 1  | 在职   |
| 2  | 在职   |
| 3  | 失业   |
| 4  | 在职   |
| 5  | 失业   |
| 6  | 在职   |

该查询将返回：

| status | COUNT(*) |
|--------|----------|
| 在职   | 4        |
| 失业   | 2        |
表示在职的人有4人，失业的人有2人。