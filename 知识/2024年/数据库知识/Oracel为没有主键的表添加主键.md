# Oracel为没有主键的表添加主键

```sql
-- 建立一个字段
ALTER TABLE NO_ID_TABLE
    ADD (ONE_NEW_ID VARCHAR2(40));
-- 加注释
comment on column NO_ID_TABLE.ONE_NEW_ID is '新的主键';

-- 建一个临时表
create table key_tmp_table as
select rownum id, USER_NO, TEAM_NO
from NO_ID_TABLE;
-- 通过临时表更新主键为行号
update NO_ID_TABLE NTB
set ONE_NEW_ID = (select id from key_tmp_table t where t.USER_NO = NTB.USER_NO AND t.TEAM_NO = NTB.TEAM_NO);
-- 删除临时表
drop table key_tmp_table;
-- 将新字段设置为主键
ALTER TABLE NO_ID_TABLE
    ADD PRIMARY KEY (ONE_NEW_ID);
```

