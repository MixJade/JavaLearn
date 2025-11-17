# 在`cloud_demo_one`数据库执行
create table cloud_demo_one.demo_user
(
    user_id  int auto_increment comment '用户ID'
        primary key,
    username varchar(20)  null comment '收件人',
    address  varchar(150) null comment '地址',
    constraint username
        unique (username)
) comment '测试用户表';

# 数据
insert into cloud_demo_one.demo_user (user_id, username, address)
values (1, '柳岩', '湖南省衡阳市'),
       (2, '文二狗', '陕西省西安市'),
       (3, '华沉鱼', '湖北省十堰市'),
       (4, '张必沉', '天津市'),
       (5, '郑爽爽', '辽宁省沈阳市大东区'),
       (6, '范兵兵', '山东省青岛市');
