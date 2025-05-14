create table prj_item
(
    item_id   int auto_increment comment '项目主键'
        primary key,
    item_name varchar(20)                  not null comment '项目名称',
    remark    varchar(50) comment '备注',
    color     varchar(7) default '#409eff' not null comment '项目颜色',
    is_del    tinyint(1) default 0         not null comment '是否作废'
) comment '项目分类表';