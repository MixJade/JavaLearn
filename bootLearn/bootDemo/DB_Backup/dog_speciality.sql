DROP TABLE IF EXISTS dog_speciality;

create table dog_speciality
(
    speciality_id   int auto_increment comment '狗的特质主键'
        primary key,
    speciality_name varchar(20) null comment '狗的特质',
    dog_id          int         not null comment '狗的主键',
    constraint dog_speciality_ibfk_1
        foreign key (dog_id) references dog (dog_id)
            on delete cascade
)
    comment '狗的特质';

INSERT INTO dog_speciality (speciality_id, speciality_name, dog_id) VALUES (1, '握手', 1);
INSERT INTO dog_speciality (speciality_id, speciality_name, dog_id) VALUES (2, '坐下', 1);
INSERT INTO dog_speciality (speciality_id, speciality_name, dog_id) VALUES (3, '安静', 1);
INSERT INTO dog_speciality (speciality_id, speciality_name, dog_id) VALUES (4, '狂暴', 2);
INSERT INTO dog_speciality (speciality_id, speciality_name, dog_id) VALUES (5, '叼飞盘', 2);
