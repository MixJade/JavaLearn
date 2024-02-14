# Oracle层次查询(connect by prior)

> 2023年12月28日 9:45:31

## 一、查询当前部门的所有下级部门

"connect by prior DEPT_ID = SUPER_DEPT_ID"是Oracle数据库中的一种层次查询语句。

这里的"DEPT_ID"和"SUPER_DEPT_ID"表示部门的ID和该部门上级部门的ID。

"connect by prior"是连接之前的记录，建立层次关系。"prior"关键字表示上一级的记录。

这条语句的意思就是，将那些上级部门ID（SUPER_DEPT_ID）与部门ID（DEPT_ID）相等的行（也就是上级部门和该部门）连接起来，以此形成一个部门与上级部门之间的层次结构。

使用这种查询方式，可以很容易地得到一种树形结构的结果，如公司的部门结构，员工的管理层次等。

```sql
select DEPT_ID,
       DEPT_NM,
       SUPER_DEPT_ID
from MY_DEPT_INFO start with DEPT_ID = '12'
connect by prior DEPT_ID = SUPER_DEPT_ID
```

这句SQL中，DEPT_ID是当前部门号，SUPER_DEPT_ID是上级部门号，这句SQL的意思是查询当前部门的所有下级部门。

## 二、查询当前部门的上级部门

我希望能够翻转一下，变成查出当前部门的所有上级部门。

可以把"connect by prior DEPT_ID = SUPER_DEPT_ID"这句语句换一下位置，变成"connect by prior SUPER_DEPT_ID = DEPT_ID"，这样就可以实现查询当前部门的所有上级部门。

完整SQL语句如下：

```sql
select DEPT_ID,
       DEPT_NM,
       SUPER_DEPT_ID
from MY_DEPT_INFO start with DEPT_ID = '12'
connect by prior SUPER_DEPT_ID = DEPT_ID
```

这样就可以查询到部门号为'12'的所有上级部门信息了，按照层级向上查询。