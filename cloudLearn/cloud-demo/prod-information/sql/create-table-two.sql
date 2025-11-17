# 在`cloud_demo_two`数据库执行
create table cloud_demo_two.demo_order
(
    order_id   int auto_increment comment '订单id'
        primary key,
    user_id    int           not null comment '用户id',
    order_name varchar(100)  null comment '商品名称',
    price      bigint        not null comment '商品价格',
    num        int default 0 null comment '商品数量'
) comment '测试订单表';

-- 数据
insert into cloud_demo_two.demo_order (order_id, user_id, order_name, price, num)
values (101, 1, 'Apple 苹果 iPhone 12 ', 699900, 1),
       (102, 2, '雅迪 yadea 新国标电动车', 209900, 1),
       (103, 3, '骆驼（CAMEL）休闲运动鞋女', 43900, 1),
       (104, 4, '小米10 双模5G 骁龙865', 359900, 1),
       (105, 5, 'OPPO Reno3 Pro 双模5G 视频双防抖', 299900, 1),
       (106, 6, '美的（Midea) 新能效 冷静星II ', 544900, 1),
       (107, 2, '西昊/SIHOO 人体工学电脑椅子', 79900, 1),
       (108, 3, '梵班（FAMDBANN）休闲男鞋', 31900, 1);