create table memo_sql
(
    memo_id      int auto_increment comment '备忘主键'
        primary key,
    memo_name    varchar(20)          not null comment '备忘名称',
    item_id      int                  not null comment '项目主键',
    remark       varchar(50) comment '备注',
    memo_content text                 not null comment '备忘内容',
    is_del       tinyint(1) default 0 not null comment '是否作废'
) comment '备忘sql表';

-- 为备忘表关联外键
ALTER TABLE memo_sql
    ADD CONSTRAINT fk_memo_prj
        FOREIGN KEY (item_id) REFERENCES prj_item (item_id)
            ON DELETE RESTRICT;