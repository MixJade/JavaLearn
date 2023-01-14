# 在mysql插入修改时查询字段

> * 有些时候，我们要通过账号插入外键id
> * 可以边插入，边通过账号查询
> * 但是这种很不方便，根据python之禅所说，简单的比复杂好
> * 但我们要会。
> * 同时这种情况目前只有两个，如果后面这种情况多了的话，
> * 我会建立一个存储静态变量的类，在授予角色时就将对应的字段设置好

## 在更新时查询

```xml
<!--修改公告信息-->
<update id="updateNotice">
    UPDATE notice a,(SELECT b.employee_id FROM employee b WHERE b.employee_username = #{username}) b2
    SET a.notice_title=#{noticeTitle},
    a.update_id=b2.employee_id,
    a.update_time=now()
    WHERE a.notice_id = #{noticeId}
</update>
```

## 在插入时查询

```xml
<!--添加公告-->
<insert id="addNotice">
    insert into notice
    (notice_title, notice_file, creat_id, create_time, update_id, update_time)
    value (#{noticeTitle},
    #{noticeFile},
    (select employee_id
    from employee
    where employee_username = #{username}),
    now(),
    creat_id,
    create_time)
</insert>
```