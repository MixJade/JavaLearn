-- 先清空，注意先清空有外键的表，再清空主表
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS societys;
-- 创建表
CREATE TABLE societys(
	societyId TINYINT PRIMARY KEY auto_increment,-- int类型 ，主键,自动增长
	societyName VARCHAR(20) NOT NULL,-- VARCHAR类型，不为空且唯一
	address VARCHAR(20) NOT NULL DEFAULT '操场', -- 最后一行千万不要加逗号
	account DOUBLE(10,2) DEFAULT 0.00
);

CREATE TABLE students(
  id INT PRIMARY KEY auto_increment, 
	studentName VARCHAR(30) NOT NULL UNIQUE, 
	sex TINYINT(1) NOT NULL DEFAULT 1,
	englishGrade INT(5),
	mathGrade INT(5),
	societyId TINYINT(1) DEFAULT 1, 
	height DOUBLE(5,2) NOT NULL,
	birthday DATE NOT NULL DEFAULT '2022-09-10',
	money INT(5) DEFAULT 0,
  -- 建立外键，注意：字段的大小和类型要和主表一致
	CONSTRAINT fk_students_societys FOREIGN KEY(societyId) REFERENCES societys(societyId)
);

-- 开始插入社团表
INSERT INTO societys(societyName) VALUES ('散人');
INSERT INTO societys(societyName,address,account) VALUES ('头文字D','秋名山',200.50),('轻音部','活动室',50000.00),('黄金之风','意大利',1000000.00),('极东魔术昼寝结社','空教室',8000.00);
-- 插入学生信息
INSERT INTO students(id,studentName,englishGrade,mathGrade,height,money) VALUES(1,'张三',80,90,1.98,500000);
-- 演示 唯一约束且为数字的id之： auto_increment自动增长
INSERT INTO students(studentName,sex,englishGrade,mathGrade,societyId,height,birthday,money) VALUES
	('夏树',0,50,40,2,1.65,'1987-02-21',8000),('拓海',1,60,60,2,1.75,'1986-12-30',5000),
	('米斯达',1,44,44,4,1.97,'1982-12-03',4444),('田井中律',0,30,40,3,1.54,'2009-08-21',6000),('秋山澪',0,100,100,3,1.60,'2009-01-15',8000),
	('琴吹䌷',0,90,90,3,1.57,'2009-07-02',50000),('平泽唯',0,30,80,3,1.56,'2009-11-27',3000),('中野梓',0,70,80,3,1.5,'2010-11-11',7000),	
	('小鸟游六花',0,67,1,5,1.50,'2012-06-12',5000),('富㭴勇太',1,80,80,5,1.7,'2012-07-07',5000),
	('凸守早苗',0,60,60,5,1.43,'2012-08-9',40000),('丹生谷森夏',0,90,90,5,1.65,'2012-8-30',5000),
	('布加拉提',1,88,90,4,1.78,'1980-09-27',100000),('乔鲁诺·乔巴拿',1,100,100,4,1.72,'1985-04-16',5000),('阿帕基',1,85,82,4,1.88,'1980-03-28',3000),
	('纳兰迦',1,2,3,4,1.70,'1983-05-20',300),('特里休',0,60,70,4,1.63,'1985-04-19',5000),
	('洪七',1,50,40,1,1.88,'1970-01-19',50000),('洪七公',1,70,70,1,1.9,'1970-02-19',50);
-- 删除重复数据
delete a from societys a,societys b where a.societyName = b.societyName and a.societyId > b.societyId;
-- 查询两表
SELECT * FROM societys;
SELECT * FROM students;
