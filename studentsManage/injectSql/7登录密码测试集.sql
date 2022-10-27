DROP TABLE IF EXISTS loginmixjade;
CREATE TABLE loginmixjade(
	idJade TINYINT PRIMARY KEY auto_increment,
	nameJade VARCHAR(20) NOT NULL,
	passwordJade VARCHAR(20) NOT NULL
);

INSERT loginmixjade(nameJade,passwordJade) VALUES('哥白金汉爵','lang0123'),
('pixiv','pixiv0223'),('1060903','322101833'),('yiban0101539','zhapian06176'),
('重载的山芋','@0418428196'),('武炎','ngrok0223'),('88048','duocai0123');

SELECT * FROM loginmixjade;