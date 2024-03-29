# Oracle恢复被误删的表

> 我不小心误删了dev数据库的表，以下是恢复的总结，记住删了以后，不要做任何操作，马上去恢复

## 前排省流

* 执行下方语句即可

```sql
FLASHBACK TABLE "你的表名" TO BEFORE DROP;
```

## 完整表述

在Oracle中，一旦你删除的表被flush，那就无法恢复了。如果你在删除之前有备份，那么可以通过恢复备份的方式找回。

但如果你使用的是Oracle10g或以上版本，你可能有机会从回收站(Recycle Bin)中恢复删除的表。

Oracle的新特性是在删除表后，表并不真的消失，而是被重命名并放到了回收站（Recycle Bin），也就是还有机会可以恢复。

使用Flashback Query可以恢复被删除的表：

1. 首先查看回收站的内容：
```sql
SQL> SHOW RECYCLEBIN;
```
这个命令会显示所有被删除的对象。

2. 如果需要恢复，可以使用下面的命令：【直接用这个就行】
```sql
SQL> FLASHBACK TABLE "你的表名" TO BEFORE DROP;
```
请使用大写字母并加双引号 "" 来指定你要恢复的表名。
