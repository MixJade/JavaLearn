DROP TABLE IF EXISTS students;

create table students
(
    id           int auto_increment
        primary key,
    studentName  varchar(30)                     not null,
    sex          tinyint(1) default 1            not null,
    englishGrade int                             null,
    mathGrade    int                             null,
    societyId    tinyint(1) default 1            null,
    height       double(5, 2)                    not null,
    birthday     date       default '2022-09-10' not null,
    money        int        default 0            null,
    constraint studentName
        unique (studentName),
    constraint fk_students_societys
        foreign key (societyId) references societys (societyId)
);

INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (1, '张三', 1, 80, 90, 1, 1.98, '2022-09-10', 500000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (2, '夏树', 0, 50, 40, 2, 1.65, '1987-02-21', 8000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (3, '拓海', 1, 60, 60, 2, 1.75, '1986-12-30', 5000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (4, '米斯达', 1, 44, 44, 4, 1.97, '1982-12-03', 4444);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (5, '田井中律', 0, 30, 40, 3, 1.54, '2009-08-21', 6000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (6, '秋山澪', 0, 100, 100, 3, 1.6, '2009-01-15', 8000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (7, '琴吹䌷', 0, 90, 90, 3, 1.57, '2009-07-02', 50000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (8, '平泽唯', 0, 30, 80, 3, 1.56, '2009-11-27', 3000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (9, '中野梓', 0, 70, 80, 3, 1.5, '2010-11-11', 7000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (10, '小鸟游六花', 0, 67, 1, 5, 1.5, '2012-06-12', 5000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (11, '富㭴勇太', 1, 80, 80, 5, 1.7, '2012-07-07', 5000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (12, '凸守早苗', 0, 60, 60, 5, 1.43, '2012-08-09', 40000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (13, '丹生谷森夏', 0, 90, 90, 5, 1.65, '2012-08-30', 5000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (14, '布加拉提', 1, 88, 90, 4, 1.78, '1980-09-27', 100000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (15, '乔鲁诺·乔巴拿', 1, 100, 100, 4, 1.72, '1985-04-16', 5000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (16, '阿帕基', 1, 85, 82, 4, 1.88, '1980-03-28', 3000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (17, '纳兰迦', 1, 2, 3, 4, 1.7, '1983-05-20', 300);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (18, '特里休', 0, 60, 70, 4, 1.63, '1985-04-19', 5000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (19, '洪七', 1, 50, 40, 1, 1.88, '1970-01-19', 50000);
INSERT INTO students (id, studentName, sex, englishGrade, mathGrade, societyId, height, birthday, money)
VALUES (20, '洪七公', 1, 70, 70, 1, 1.9, '1970-02-19', 50);
