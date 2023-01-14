/*
Navicat MySQL Data Transfer

Source Server         : 天下无双
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : pet_forge

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2023-01-12 21:54:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `adopt`
-- ----------------------------
DROP TABLE IF EXISTS `adopt`;
CREATE TABLE `adopt` (
  `adopt_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '领养表的id',
  `adopt_code` varchar(16) NOT NULL COMMENT '订单编号',
  `pet_id` bigint(20) NOT NULL COMMENT '领养宠物的id',
  `client_id` bigint(20) NOT NULL COMMENT '领养人id',
  `adopt_money` int(12) NOT NULL DEFAULT '0' COMMENT '领养押金',
  `adopt_info` varchar(64) DEFAULT NULL COMMENT '订单备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间，也是领养时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`adopt_id`),
  UNIQUE KEY `adopt_code` (`adopt_code`,`is_del`),
  KEY `pet_id` (`pet_id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `adopt_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`),
  CONSTRAINT `adopt_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='领养宠物订单';

-- ----------------------------
-- Records of adopt
-- ----------------------------
INSERT INTO `adopt` VALUES ('1', '20001', '26', '1', '233', '领养三毛', '2023-01-12 16:45:49', '2023-01-12 21:47:51', '0');
INSERT INTO `adopt` VALUES ('2', '20002', '34', '3', '300', '小狐狸^_^', '2023-01-12 21:38:33', '2023-01-12 21:38:33', '0');
INSERT INTO `adopt` VALUES ('3', '20003', '22', '3', '10', '五花比四花多一花', '2023-01-12 21:51:59', '2023-01-12 21:51:59', '0');

-- ----------------------------
-- Table structure for `appointment`
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `appointment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '挂号单id',
  `client_id` bigint(20) NOT NULL COMMENT '用户id',
  `pet_id` bigint(20) NOT NULL COMMENT '宠物id',
  `appointment_date` datetime NOT NULL COMMENT '就诊时间',
  `department_id` bigint(20) NOT NULL COMMENT '部门id',
  `doctor_id` bigint(20) NOT NULL COMMENT '医生id',
  `appointment_info` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '挂号简短信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`appointment_id`),
  KEY `fk_appointment_client` (`client_id`),
  KEY `fk_appointment_pet` (`pet_id`),
  KEY `fk_appointment_department` (`department_id`),
  KEY `fk_appointment_doctor` (`doctor_id`),
  CONSTRAINT `fk_appointment_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `fk_appointment_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `fk_appointment_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_appointment_pet` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='挂号单表';

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES ('1', '1', '5', '2022-12-22 11:17:54', '1', '1', '这只猫患上了严重的肛裂', '2022-12-22 11:18:04', '2022-12-22 11:18:00', '0');
INSERT INTO `appointment` VALUES ('2', '4', '5', '2023-01-08 21:03:00', '4', '5', '尝试第一次挂号', '2023-01-08 21:04:35', '2023-01-09 13:26:39', '0');
INSERT INTO `appointment` VALUES ('3', '3', '12', '2023-01-08 23:30:00', '6', '4', '熬夜写网页，我需要麻醉', '2023-01-08 23:31:23', '2023-01-09 13:20:13', '0');

-- ----------------------------
-- Table structure for `client`
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `client_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `client_username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户账号',
  `client_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `client_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓名',
  `client_gender` tinyint(1) DEFAULT '0' COMMENT '用户性别',
  `client_tel` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户电话',
  `client_age` date DEFAULT NULL COMMENT '用户生日',
  `client_info` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户简介',
  `client_photo` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户照片名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `client_username` (`client_username`,`is_del`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('1', 'ying', '6b6864bf70c40ccbc2752cd9ef11e77b', '莹', '0', '13882244666', '2001-01-03', '来自提瓦特', 'ying.jpg', '2022-12-21 19:24:32', '2023-01-08 16:18:17', '0');
INSERT INTO `client` VALUES ('2', 'lei-jun', '6b6864bf70c40ccbc2752cd9ef11e77b', '雷军', '0', '15822446667', '1990-01-01', '喜欢宅家撸猫', 'lei-jun.jpg', '2023-01-01 20:03:13', '2023-01-08 16:14:02', '0');
INSERT INTO `client` VALUES ('3', 'sanyu', '6b6864bf70c40ccbc2752cd9ef11e77b', '散玉', '1', 'QQ-1114185977', '2000-01-01', '作者', 'san-yu.jpg', '2023-01-01 21:07:33', '2023-01-09 13:20:26', '0');
INSERT INTO `client` VALUES ('4', 'fox', '6b6864bf70c40ccbc2752cd9ef11e77b', '狐狸', '0', '15822446667', '2000-01-07', '粉毛狐狸', 'ba-chong.jpg', '2023-01-07 16:01:10', '2023-01-08 16:13:26', '0');

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `department_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '科室id',
  `department_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '科室名称',
  `department_info` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科室简介',
  `department_address` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '科室地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`department_id`),
  UNIQUE KEY `department_name` (`department_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '犬猫内科', '主要整治猫猫狗狗', '第一大楼227', '2022-12-22 11:11:19', '2023-01-08 15:48:34', '0');
INSERT INTO `department` VALUES ('2', '骨科', '专治跌打损伤', '第三大楼238', '2023-01-03 21:00:02', '2023-01-07 17:40:51', '0');
INSERT INTO `department` VALUES ('3', '软组织外科', '治各种骨与软组织良恶性肿瘤', '第三大楼226', '2023-01-03 21:02:17', '2023-01-03 21:02:30', '0');
INSERT INTO `department` VALUES ('4', '美容室', '对宠物进行美容', '第三大楼227', '2023-01-03 21:04:50', '2023-01-03 21:04:50', '0');
INSERT INTO `department` VALUES ('5', '麻醉科', '进行麻醉操作', '第一大楼226', '2023-01-03 21:05:28', '2023-01-03 21:05:28', '0');
INSERT INTO `department` VALUES ('6', '影像科', '拍摄X光片', '第二大楼228', '2023-01-03 21:09:22', '2023-01-03 21:10:17', '0');
INSERT INTO `department` VALUES ('7', '内科', '内科', '第一大楼229', '2023-01-03 21:10:08', '2023-01-03 21:10:08', '0');
INSERT INTO `department` VALUES ('8', '中药科', '使用传统中药医治', '第一大楼225', '2023-01-07 20:11:51', '2023-01-07 20:11:51', '0');

-- ----------------------------
-- Table structure for `doctor`
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `doctor_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '医生id',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `doctor_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医生工号',
  `doctor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医生姓名',
  `doctor_gender` tinyint(1) NOT NULL DEFAULT '0' COMMENT '医生性别，1男0女',
  `doctor_age` date NOT NULL COMMENT '医生生日',
  `doctor_photo` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医生照片名称',
  `doctor_tel` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '医生联系方式',
  `doctor_job` varchar(16) DEFAULT NULL COMMENT '医生职位',
  `doctor_info` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '医生简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`doctor_id`),
  UNIQUE KEY `doctor_code` (`doctor_code`) USING BTREE,
  KEY `fk_doctor_department` (`department_id`),
  CONSTRAINT `fk_doctor_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='医生表，外键部门';

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES ('1', '1', '10001', '童德统', '1', '1990-10-10', 'doctor-1.jpg', '13882244666', '副院长', '国内著名医学专家，擅长绝育', '2022-12-22 11:16:11', '2023-01-08 15:50:59', '0');
INSERT INTO `doctor` VALUES ('2', '5', '10002', '爱丽丝', '0', '2003-02-01', 'doctor-2.jpg', '13882244666', '麻醉科主任', '擅长麻醉', '2023-01-06 12:25:54', '2023-01-06 12:27:24', '0');
INSERT INTO `doctor` VALUES ('4', '6', '10003', '汤姆', '1', '2023-01-11', 'doctor-3.jpg', '13882244666', '医生', '擅长x光拍片', '2023-01-06 12:29:10', '2023-01-06 12:29:10', '0');
INSERT INTO `doctor` VALUES ('5', '4', '10004', '张嘴', '0', '2002-01-05', 'doctor-4.jpg', '13882244666', '医生', '著名宠物医美专家', '2023-01-06 12:30:19', '2023-01-06 12:30:19', '0');
INSERT INTO `doctor` VALUES ('6', '3', '10005', '浩克', '1', '2001-01-06', 'doctor-5.jpg', '13882244666', '医生', '多年老中医，专治跌打损伤', '2023-01-06 12:31:30', '2023-01-06 12:31:30', '0');
INSERT INTO `doctor` VALUES ('8', '2', '10006', '李正骨', '0', '2000-01-28', 'admm.jpg', '13882244666', '医生', '国内外著名骨科专家', '2023-01-07 16:35:23', '2023-01-07 19:58:11', '0');

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employee_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `employee_username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '管理员账号',
  `employee_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '管理员密码',
  `employee_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '管理员姓名',
  `employee_level` int(8) NOT NULL DEFAULT '0' COMMENT '管理员等级',
  `employee_tel` varchar(12) NOT NULL COMMENT '管理员联系方式',
  `employee_photo` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '管理员照片名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `employee_username` (`employee_username`,`is_del`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', 'admin', '6b6864bf70c40ccbc2752cd9ef11e77b', '炒鸡管理员', '6', '13882244666', 'admm.jpg', '2022-12-22 11:08:31', '2023-01-12 12:23:32', '0');
INSERT INTO `employee` VALUES ('2', 'yun', 'af2292f0a8074e588713093386058d4d', '李云', '4', '13882244666', 'doctor-1.jpg', '2023-01-05 21:34:23', '2023-01-12 10:35:50', '0');
INSERT INTO `employee` VALUES ('3', 'ra9', '6b6864bf70c40ccbc2752cd9ef11e77b', '马库斯', '2', '13882244666', 'ju-cat.jpg', '2023-01-05 22:02:30', '2023-01-12 10:36:05', '0');
INSERT INTO `employee` VALUES ('4', 'hua', '6b6864bf70c40ccbc2752cd9ef11e77b', '刘德华', '4', '13882244666', 'san-yu.jpg', '2023-01-07 20:25:08', '2023-01-12 12:43:28', '0');
INSERT INTO `employee` VALUES ('5', 'zs', '6b6864bf70c40ccbc2752cd9ef11e77b', '张三', '2', '13882244666', 'zs.jpg', '2023-01-12 12:25:41', '2023-01-12 12:43:14', '0');

-- ----------------------------
-- Table structure for `foster`
-- ----------------------------
DROP TABLE IF EXISTS `foster`;
CREATE TABLE `foster` (
  `foster_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '寄养表的id',
  `foster_code` varchar(16) NOT NULL COMMENT '订单编号',
  `pet_id` bigint(20) NOT NULL COMMENT '寄养宠物的id',
  `client_id` bigint(20) NOT NULL COMMENT '寄养人id',
  `foster_term` date NOT NULL COMMENT '寄养到期时间',
  `foster_money` int(12) NOT NULL DEFAULT '0' COMMENT '寄养押金',
  `foster_info` varchar(64) DEFAULT NULL COMMENT '订单备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`foster_id`),
  UNIQUE KEY `foster_code` (`foster_code`,`is_del`),
  KEY `pet_id` (`pet_id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `foster_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`),
  CONSTRAINT `foster_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='寄养表';

-- ----------------------------
-- Records of foster
-- ----------------------------
INSERT INTO `foster` VALUES ('1', '10001', '8', '1', '2023-01-28', '100', '寄养波斯', '2023-01-12 16:44:32', '2023-01-12 21:51:29', '0');
INSERT INTO `foster` VALUES ('2', '10002', '32', '3', '2023-01-13', '800', '好好照顾', '2023-01-12 20:04:22', '2023-01-12 20:04:22', '0');
INSERT INTO `foster` VALUES ('3', '10003', '33', '2', '2023-07-28', '20000', '调教ing', '2023-01-12 20:08:46', '2023-01-12 21:48:02', '0');
INSERT INTO `foster` VALUES ('4', '10004', '5', '4', '2023-07-13', '300', '', '2023-01-12 20:10:45', '2023-01-12 20:10:45', '0');
INSERT INTO `foster` VALUES ('5', '10005', '32', '3', '2023-01-14', '200', '有事外出', '2023-01-12 21:52:28', '2023-01-12 21:52:40', '0');

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `notice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `notice_title` varchar(64) NOT NULL COMMENT '公告标题',
  `notice_file` varchar(64) NOT NULL COMMENT '文本文件名称',
  `creat_id` bigint(20) NOT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `is_del` varchar(32) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`notice_id`),
  KEY `creat_id` (`creat_id`),
  KEY `update_id` (`update_id`),
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`creat_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='公告表';

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', '宠物医院第一个公告', 'notice-1.txt', '1', '2023-01-02 17:22:55', '3', '2023-01-11 19:42:12', '0', '0');
INSERT INTO `notice` VALUES ('4', '论纯白', '4d7dcc2e-7465-49aa-b476-c85a255162a9.txt', '2', '2023-01-11 17:52:06', '2', '2023-01-11 17:52:58', '1', '0');
INSERT INTO `notice` VALUES ('5', '保障粮食安全的重要性', 'f91d4670-9d59-44c8-af5d-b74ef6f4dc76.txt', '2', '2023-01-11 17:53:56', '3', '2023-01-11 19:38:27', '0', '0');
INSERT INTO `notice` VALUES ('6', 'dd', 'fa9c33fc-1bb0-446f-8a76-5aed34228976.txt', '3', '2023-01-11 17:54:22', '3', null, '0', '2023-01-11 18:05:16');
INSERT INTO `notice` VALUES ('7', '加强耕地利用，是粮食安全的基石', '4b50890c-a992-4d15-b5da-1dc26306df12.txt', '3', '2023-01-11 19:08:19', '3', '2023-01-11 19:44:33', '0', '0');
INSERT INTO `notice` VALUES ('8', '加强节粮减损，杜绝粮食浪费', '9a9214e5-333c-4146-9f96-4a5c00b52215.txt', '3', '2023-01-11 19:09:20', '3', '2023-01-11 19:43:22', '0', '0');

-- ----------------------------
-- Table structure for `pet`
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet` (
  `pet_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '宠物的id',
  `pet_name` varchar(16) NOT NULL DEFAULT '未名' COMMENT '宠物姓名',
  `pet_variety` varchar(16) NOT NULL DEFAULT '未知' COMMENT '宠物的品种',
  `pet_sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '宠物性别，0母1公',
  `pet_age` date NOT NULL COMMENT '宠物生日',
  `pet_status` varchar(16) DEFAULT '健康' COMMENT '宠物状态',
  `pet_bed` tinyint(1) DEFAULT '0' COMMENT '是否住院',
  `client_id` bigint(20) DEFAULT NULL COMMENT '宠物主人id',
  `pet_photo` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '宠物照片名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` varchar(32) NOT NULL DEFAULT '0' COMMENT '逻辑删除，默认0，填充删除日期',
  PRIMARY KEY (`pet_id`),
  KEY `fk_pet_client` (`client_id`) USING BTREE,
  CONSTRAINT `fk_pet_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='宠物信息表，外键用户表';

-- ----------------------------
-- Records of pet
-- ----------------------------
INSERT INTO `pet` VALUES ('1', 'saber', '金毛犬', '0', '2005-01-13', '怀孕待产', '0', '1', null, '2022-11-30 15:52:47', '2022-12-01 15:52:53', '2023-01-07 19:12:53');
INSERT INTO `pet` VALUES ('2', '甘雨', '椰羊', '0', '1905-01-13', '频繁掉毛', '1', '1', null, '2022-11-30 15:53:07', '2022-12-01 15:53:10', '2023-01-07 19:12:55');
INSERT INTO `pet` VALUES ('3', '心海', '观赏鱼', '0', '2000-01-13', '非常健康', '0', '1', 'kokomi.jpg', '2022-11-30 15:55:04', '2023-01-09 12:58:34', '0');
INSERT INTO `pet` VALUES ('4', '神里凌华', '柴犬', '0', '2004-01-13', '肛裂', '1', '1', null, '2022-11-30 15:56:41', '2022-12-01 15:56:48', '2023-01-07 19:17:09');
INSERT INTO `pet` VALUES ('5', '雷电小兵', '吉娃娃', '1', '1990-08-09', '绝育手术', '1', '4', 'liu-lang.jpg', '2022-11-30 15:58:30', '2023-01-12 20:43:21', '0');
INSERT INTO `pet` VALUES ('6', '笔试测试调试', '但是', '1', '2022-12-29', '良好', '0', null, null, '2022-12-23 10:55:30', '2022-12-23 10:55:30', '2023-01-07 19:17:09');
INSERT INTO `pet` VALUES ('7', '波一斯', '波斯猫', '0', '2016-12-15', '频繁掉毛', '0', '1', 'boss-cat.jpg', '2022-12-23 11:03:06', '2023-01-02 19:53:22', '0');
INSERT INTO `pet` VALUES ('8', '波二斯', '波斯猫', '1', '2016-12-17', '良好', '0', '1', 'boss-cat.jpg', '2022-12-23 11:07:29', '2023-01-02 17:39:11', '0');
INSERT INTO `pet` VALUES ('9', '波三斯', '波斯猫', '0', '2013-12-23', '频繁掉毛', '0', '1', 'boss-cat.jpg', '2022-12-23 11:23:08', '2023-01-07 19:44:13', '0');
INSERT INTO `pet` VALUES ('10', '波四斯', '波斯猫', '0', '2012-12-03', '良好', '0', '2', 'boss-cat.jpg', '2022-12-23 11:25:13', '2023-01-06 19:11:10', '0');
INSERT INTO `pet` VALUES ('11', '大橘莹', '橘猫', '0', '2007-12-23', '体重超标', '0', '2', 'ju-cat.jpg', '2022-12-23 11:36:09', '2023-01-08 16:49:05', '0');
INSERT INTO `pet` VALUES ('12', '二橘', '橘猫', '1', '2009-12-24', '体重超标', '0', '3', 'ju-cat.jpg', '2022-12-23 11:40:07', '2023-01-07 12:13:37', '0');
INSERT INTO `pet` VALUES ('13', '三橘', '橘猫', '0', '2009-12-23', '良好', '1', '3', 'ju-cat.jpg', '2022-12-23 11:42:23', '2023-01-07 11:42:00', '0');
INSERT INTO `pet` VALUES ('14', '四橘', '橘猫', '1', '2010-12-23', '需要绝育', '0', '3', 'ju-cat.jpg', '2022-12-23 11:43:21', '2023-01-07 15:47:15', '0');
INSERT INTO `pet` VALUES ('15', '五橘', '橘猫', '0', '2012-12-23', '头部肿胀', '1', null, '', '2022-12-23 11:49:51', '2022-12-23 11:49:51', '2023-01-07 19:17:16');
INSERT INTO `pet` VALUES ('16', '五橘', '橘猫', '0', '2012-12-23', '头部肿胀', '1', null, 'ju-cat.jpg', '2022-12-23 11:49:59', '2022-12-23 11:49:59', '0');
INSERT INTO `pet` VALUES ('17', '六橘', '橘猫', '1', '2012-12-23', '发情期', '1', null, 'ju-cat.jpg', '2022-12-23 16:34:36', '2023-01-07 15:40:03', '0');
INSERT INTO `pet` VALUES ('18', '狸大花', '狸花猫', '0', '2012-12-23', '发情期', '0', '2', 'li-hua.jpg', '2022-12-23 17:25:49', '2023-01-01 22:19:14', '0');
INSERT INTO `pet` VALUES ('19', '狸二花', '狸花猫', '1', '2013-12-23', '需要绝育', '1', null, 'li-hua.jpg', '2022-12-23 17:28:29', '2022-12-28 21:24:26', '0');
INSERT INTO `pet` VALUES ('20', '狸三花', '狸花猫', '0', '2012-12-23', '发情期', '0', null, 'li-hua.jpg', '2022-12-23 18:36:17', '2022-12-23 18:36:17', '0');
INSERT INTO `pet` VALUES ('21', '狸四花', '狸花猫', '0', '2013-12-30', '需要绝育', '1', null, 'li-hua.jpg', '2022-12-23 19:07:33', '2022-12-23 19:07:33', '0');
INSERT INTO `pet` VALUES ('22', '狸五花', '狸花猫', '0', '2018-12-23', '体重超标', '0', '3', 'li-hua.jpg', '2022-12-23 19:34:04', '2022-12-23 19:34:04', '0');
INSERT INTO `pet` VALUES ('23', '狸六花', '狸花猫', '0', '2022-12-24', '频繁掉毛', '0', null, 'li-hua.jpg', '2022-12-23 19:37:43', '2022-12-25 10:50:50', '0');
INSERT INTO `pet` VALUES ('24', '金大毛', '金毛犬是', '0', '2012-12-24', '良好', '0', null, 'jin-mao.jpg', '2022-12-24 10:51:45', '2023-01-07 18:56:24', '0');
INSERT INTO `pet` VALUES ('25', '金二毛', '金毛犬', '0', '2014-12-24', '发情期', '0', null, 'jin-mao.jpg', '2022-12-24 11:13:54', '2022-12-24 11:13:54', '0');
INSERT INTO `pet` VALUES ('26', '金三毛', '金毛犬', '1', '2012-12-31', '发情期', '0', '1', 'jin-mao.jpg', '2022-12-29 15:38:38', '2022-12-29 15:53:05', '0');
INSERT INTO `pet` VALUES ('27', '金四毛', '金毛犬', '0', '2012-12-29', '乱叫', '0', null, 'jin-mao.jpg', '2022-12-29 15:52:09', '2022-12-29 17:48:58', '0');
INSERT INTO `pet` VALUES ('28', '金五毛', '金毛犬', '1', '2022-12-30', '体重超标', '0', null, 'jin-mao.jpg', '2022-12-30 23:10:04', '2023-01-09 13:01:38', '0');
INSERT INTO `pet` VALUES ('29', '布大偶', '布偶猫', '0', '2012-12-30', '脱水', '0', null, 'bu-ou.jpg', '2022-12-30 23:10:22', '2023-01-09 13:01:47', '0');
INSERT INTO `pet` VALUES ('30', '金七毛', '金毛犬', '0', '2022-12-30', '频繁掉毛', '0', null, '', '2022-12-30 23:19:56', '2022-12-30 23:19:56', '2023-01-07 19:17:22');
INSERT INTO `pet` VALUES ('31', '布小偶', '布偶猫', '0', '2012-12-31', '频繁掉毛', '0', null, 'bu-ou.jpg', '2022-12-30 23:22:07', '2022-12-30 23:22:07', '0');
INSERT INTO `pet` VALUES ('32', '布偶二', '布偶猫', '1', '2012-12-30', '良好', '1', '3', 'bu-ou.jpg', '2022-12-30 23:24:18', '2023-01-01 22:19:00', '0');
INSERT INTO `pet` VALUES ('33', '八重神子', '藏狐', '0', '2013-01-01', '需要绝育', '1', '2', 'ba-chong.jpg', '2023-01-01 21:00:05', '2023-01-01 21:00:05', '0');
INSERT INTO `pet` VALUES ('34', '九重狐狸', '粉毛狐狸', '0', '2013-01-10', '良好', '0', '3', 'ba-chong.jpg', '2023-01-10 21:10:44', '2023-01-10 21:10:44', '0');
