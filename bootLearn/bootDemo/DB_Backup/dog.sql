DROP TABLE IF EXISTS dog;

create table dog
(
    dog_id   int(20) auto_increment comment '狗的主键'
        primary key,
    dog_name varchar(20) default '旺财' not null comment '狗的名字',
    sex      tinyint(1)  default 0      not null comment '性别，1公0母',
    age      int(20)     default 0      null comment '年龄',
    breed    varchar(20) default '土狗' not null comment '品种'
)
    comment '狗';

INSERT INTO dog (dog_id, dog_name, sex, age, breed) VALUES (1, '小黑', 0, 0, '土狗');
INSERT INTO dog (dog_id, dog_name, sex, age, breed) VALUES (2, '旺财', 1, 0, '土狗');
