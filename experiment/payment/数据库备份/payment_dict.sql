create table payment_dict
(
    payment_type int auto_increment comment '字典主键'
        primary key,
    key_name     varchar(16)              not null comment '类型名称',
    is_income    tinyint(1)  default 0    not null comment '1收入0支出',
    big_type     int                      not null comment '字典大类,存于代码',
    remark       varchar(50) default '无' not null comment '备注'
) comment '收支类型表';

insert into payment.payment_dict (payment_type, key_name, is_income, big_type, remark)
values (1, '地铁', 0, 2, ''),
       (2, '话费', 0, 6, ''),
       (3, '理发', 0, 6, ''),
       (4, '银行短信', 0, 11, ''),
       (5, '工资', 1, 9, ''),
       (6, '补贴', 1, 9, ''),
       (7, '堂食', 0, 1, ''),
       (8, '外卖', 0, 1, ''),
       (9, '买菜', 0, 1, ''),
       (10, '红包收入', 1, 6, ''),
       (11, '喝茶', 0, 1, ''),
       (12, '预制菜', 0, 1, '面包、方便面'),
       (13, '红包发出', 0, 6, ''),
       (14, '游戏', 0, 4, ''),
       (15, '打印', 0, 6, ''),
       (16, '淘宝', 0, 7, ''),
       (17, '未知网购', 0, 7, ''),
       (18, '未知支出', 0, 12, ''),
       (19, '银行利息', 1, 11, ''),
       (20, '房租', 0, 3, ''),
       (21, '机票', 0, 2, ''),
       (22, '小说', 0, 4, ''),
       (23, '超市购物', 0, 7, '');