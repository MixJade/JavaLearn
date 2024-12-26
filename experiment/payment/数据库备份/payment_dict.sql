create table payment_dict
(
    payment_type int auto_increment comment '字典主键'
        primary key,
    key_name     varchar(16)          not null comment '类型名称',
    is_income    tinyint(1) default 0 not null comment '1收入0支出',
    big_type     int                  not null comment '字典大类,存于代码',
    color        varchar(10)          not null comment '分类颜色'
) comment '收支类型表';

insert into payment_dict (payment_type, key_name, is_income, big_type, color)
values (1, '地铁', 0, 2, '#1E90FF'),
       (2, '话费', 0, 6, '#008000'),
       (3, '理发', 0, 6, '#008000'),
       (4, '银行短信', 0, 11, '#000080'),
       (5, '工资', 1, 9, '#00A9A9'),
       (6, '补贴', 1, 9, '#00A9A9'),
       (7, '堂食', 0, 1, '#FF4500'),
       (8, '外卖', 0, 1, '#FF4500'),
       (9, '买菜', 0, 1, '#FF4500'),
       (10, '红包收入', 1, 6, '#008000'),
       (11, '喝茶', 0, 1, '#FF4500'),
       (12, '预制菜', 0, 1, '#FF4500'),
       (13, '红包发出', 0, 6, '#008000'),
       (14, '游戏', 0, 4, '#FF1493'),
       (15, '打印', 0, 6, '#008000'),
       (16, '淘宝', 0, 7, '#746406'),
       (17, '未知网购', 0, 7, '#B59900'),
       (18, '未知支出', 0, 12, '#696969'),
       (19, '银行利息', 1, 11, '#000080'),
       (20, '房租', 0, 3, '#6B8E23'),
       (21, '机票', 0, 2, '#1E90FF'),
       (22, '小说', 0, 4, '#FF1493'),
       (23, '超市购物', 0, 7, '#B59900'),
       (24, '寄快递', 0, 6, '#008000'),
       (25, '骑行', 0, 2, '#1e90ff');