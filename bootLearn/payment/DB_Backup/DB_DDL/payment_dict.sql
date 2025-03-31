create table payment_dict
(
    payment_type int auto_increment comment '字典主键'
        primary key,
    key_name     varchar(16)          not null comment '类型名称',
    is_income    tinyint(1) default 0 not null comment '1收入0支出',
    big_type     int                  not null comment '字典大类,存于代码',
    color        varchar(10)          not null comment '分类颜色'
) comment '收支类型表';