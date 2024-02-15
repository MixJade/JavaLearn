# Oracle数据库替换敏感词

> 2024年2月29日20:12:46

### 一、需求

要求更新my_table表中的dog_name字段，要求如果dog_name中含有"张三"，者改为"XX"，比如："张三狗"要被更新为"XX狗"。

### 二、方案

你可以使用Oracle的 `REPLACE` 函数来替换字符串中的指定部分。下面是一个示例SQL。

```sql
UPDATE my_table
SET dog_name = REPLACE(dog_name, '张三', 'XX')
WHERE dog_name LIKE '%张三%';
```

上面的SQL会查找 `my_table` 表中 `dog_name` 字段包含 "张三" 的所有记录，然后将 "张三" 替换为 "XX"。例如，如果字段原来的值是 "张三狗"，那么更新后的值将是 "XX狗"。