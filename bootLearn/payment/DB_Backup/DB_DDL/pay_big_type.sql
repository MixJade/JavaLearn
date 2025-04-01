create table pay_big_type
(
    typeKey  int auto_increment comment '大类主键'
        primary key,
    key_name varchar(4)  not null comment '大类名称',
    color    varchar(10) not null comment '大类颜色'
) comment '收支大类表';

-- 为字典表关联外键
ALTER TABLE payment_dict
    ADD CONSTRAINT fk_payment_dict_big_type
        FOREIGN KEY (big_type) REFERENCES pay_big_type (typeKey)
            ON DELETE RESTRICT;