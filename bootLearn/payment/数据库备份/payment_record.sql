create table payment_record
(
    record_id    int auto_increment comment '记录主键'
        primary key,
    payment_type int            default 1            not null comment '收支类型',
    is_income    tinyint(1)     default 0            not null comment '1收入0支出',
    money        decimal(10, 2) default 0.00         not null comment '金额',
    pay_date     date           default '2023-01-01' not null comment '付费时间',
    remark       varchar(50)    default '无'         not null comment '备注'
) comment '收支记录表';
