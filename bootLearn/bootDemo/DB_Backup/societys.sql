DROP TABLE IF EXISTS societys;

create table societys
(
    societyId   tinyint auto_increment
        primary key,
    societyName varchar(20)                  not null,
    address     varchar(20)   default '操场' not null,
    account     double(10, 2) default 0.00   null
);

INSERT INTO societys (societyId, societyName, address, account)
VALUES (1, '散人', '操场', 0);
INSERT INTO societys (societyId, societyName, address, account)
VALUES (2, '头文字D', '秋名山', 200.5);
INSERT INTO societys (societyId, societyName, address, account)
VALUES (3, '轻音部', '活动室', 50000);
INSERT INTO societys (societyId, societyName, address, account)
VALUES (4, '黄金之风', '意大利', 1000000);
INSERT INTO societys (societyId, societyName, address, account)
VALUES (5, '极东魔术昼寝结社', '空教室', 8000);
