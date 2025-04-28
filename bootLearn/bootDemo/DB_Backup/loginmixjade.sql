DROP TABLE IF EXISTS loginmixjade;

create table loginmixjade
(
    idJade       tinyint auto_increment
        primary key,
    nameJade     varchar(20) not null,
    passwordJade varchar(20) not null
);

INSERT INTO loginmixjade (idJade, nameJade, passwordJade)
VALUES (1, 'Ali', '123456');
INSERT INTO loginmixjade (idJade, nameJade, passwordJade)
VALUES (2, 'Annie', '123456');
INSERT INTO loginmixjade (idJade, nameJade, passwordJade)
VALUES (3, 'Alger', '123456');
INSERT INTO loginmixjade (idJade, nameJade, passwordJade)
VALUES (4, 'Augustine', '123456');
INSERT INTO loginmixjade (idJade, nameJade, passwordJade)
VALUES (5, 'Archibald', '123456');
INSERT INTO loginmixjade (idJade, nameJade, passwordJade)
VALUES (6, 'Aldrich', '123456');
INSERT INTO loginmixjade (idJade, nameJade, passwordJade)
VALUES (7, 'Arvin', '123456');
