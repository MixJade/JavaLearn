DROP TABLE IF EXISTS loginmixjade;
CREATE TABLE loginmixjade(
	idJade TINYINT PRIMARY KEY auto_increment,
	nameJade VARCHAR(20) NOT NULL,
	passwordJade VARCHAR(20) NOT NULL
);

INSERT loginmixjade(nameJade,passwordJade) VALUES('Ali','123456'),
('Annie','123456'),('Alger','123456'),('Augustine','123456'),
('Archibald','123456'),('Aldrich','123456'),('Arvin','123456');;

SELECT * FROM loginmixjade;