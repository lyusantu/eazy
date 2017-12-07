/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : eazy

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-07 17:16:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sign`
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `sign_in_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sign
-- ----------------------------
INSERT INTO `sign` VALUES ('1', '1', '2017-12-07 17:10:20', '0');
INSERT INTO `sign` VALUES ('2', '1', '2017-12-07 17:10:25', '0');
INSERT INTO `sign` VALUES ('3', '1', '2017-12-07 17:10:29', '0');
INSERT INTO `sign` VALUES ('4', '1', '2017-12-07 17:10:32', '0');
INSERT INTO `sign` VALUES ('5', '1', '2017-12-07 17:11:43', '0');
INSERT INTO `sign` VALUES ('6', '1', '2017-12-07 17:11:45', '0');
INSERT INTO `sign` VALUES ('7', '1', '2017-12-07 17:11:45', '0');
INSERT INTO `sign` VALUES ('8', '1', '2017-12-07 17:11:46', '0');
INSERT INTO `sign` VALUES ('9', '1', '2017-12-07 17:11:48', '0');
INSERT INTO `sign` VALUES ('10', '1', '2017-12-07 17:11:49', '0');
INSERT INTO `sign` VALUES ('11', '1', '2017-12-07 17:11:50', '0');
INSERT INTO `sign` VALUES ('12', '1', '2017-12-07 17:11:50', '0');
INSERT INTO `sign` VALUES ('13', '1', '2017-12-07 17:15:48', '0');
INSERT INTO `sign` VALUES ('14', '1', '2017-12-07 17:15:56', '0');
INSERT INTO `sign` VALUES ('15', '1', '2017-12-07 17:15:59', '0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `password` varchar(255) NOT NULL,
  `balance` bigint(20) NOT NULL COMMENT '账户余额',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  `reg_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `status` int(11) DEFAULT NULL COMMENT '状态 [1:"正常",0:"未验证",2:"禁言",3:"封禁"]',
  `auth` varchar(255) DEFAULT NULL,
  `vip` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '352050321@qq.com', '超人', '0ab44bd43d6b18fcd5cd928d6faab1b8', '100', 'http://oih7sazbd.bkt.clouddn.com/FivibFbr6k8GqJBdZBea56zq0S4X', '0', '', '2017-12-07 16:16:06', '深圳', '0', 'eazy作者', '3');
