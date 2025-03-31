create table payment_cache
(
    cache_id  int auto_increment comment '缓存主键'
        primary key,
    pay_date  date           default '2023-01-01' not null comment '付费时间',
    pay_type  varchar(50)    default '无'         not null comment '交易类型',
    pay_man   varchar(50)    default '无'         not null comment '交易对方',
    ware_name varchar(50)    default '无'         not null comment '商品名称',
    is_income tinyint(1)     default 0            not null comment '1收入0支出',
    pay_way   varchar(50)    default '无'         not null comment '支付方式',
    pay_state varchar(50)    default '无'         not null comment '当前状态',
    money     decimal(10, 2) default 0.00         not null comment '金额',
    is_del    tinyint(1)     default 0            not null comment '是否失效,1是0否'
) comment '收支缓存表(微信导入)';
