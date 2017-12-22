/*
Navicat MySQL Data Transfer

Source Server         : eazy
Source Server Version : 50638
Source Host           : 112.74.54.119:3306
Source Database       : eazy

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2017-12-22 15:15:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `collection`
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `collection_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('28', '17', '1', '2017-12-21 13:54:40');
INSERT INTO `collection` VALUES ('29', '18', '1', '2017-12-21 14:53:51');

-- ----------------------------
-- Table structure for `column`
-- ----------------------------
DROP TABLE IF EXISTS `column`;
CREATE TABLE `column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `suffix` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of column
-- ----------------------------
INSERT INTO `column` VALUES ('1', '提问', 'quiz', '1');
INSERT INTO `column` VALUES ('2', '分享', 'share', '1');
INSERT INTO `column` VALUES ('3', '讨论', 'discuss', '1');
INSERT INTO `column` VALUES ('4', '建议', 'suggest', '1');
INSERT INTO `column` VALUES ('5', '公告', 'notice', '2');
INSERT INTO `column` VALUES ('6', '动态', 'news', '2');

-- ----------------------------
-- Table structure for `post`
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `content` text NOT NULL,
  `reward` int(11) NOT NULL COMMENT '奖励',
  `delete` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `author` int(11) NOT NULL,
  `comments` int(11) DEFAULT NULL,
  `readers` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '状态:未结/已结',
  `top` int(11) NOT NULL,
  `wonderful` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('16', 'eazy社区未完成版本说明', '2', '凡提示“请求异常，请重试”的功能，均为未完成功能 face[嘻嘻] \n\nimg[https://static.oschina.net/uploads/space/2017/1220/143949_dmar_2903254.jpg] ', '20', '0', '2017-11-20 16:06:05', '1', '0', '0', '0', '1', '1');
INSERT INTO `post` VALUES ('17', '占位文章', '3', 'face[思考] face[思考] face[思考] ', '20', '0', '2017-12-20 16:06:44', '1', '0', '0', '0', '0', '0');
INSERT INTO `post` VALUES ('18', '测试发表帖子', '2', '测试代码\n[pre]\n                            upload.render({\n                                elem: \'#uploadImg\'\n                                , url: \'/api/upload/\'\n                                , size: 200\n                                , done: function (res) {\n                                    if (res.status == 0) {\n                                        image.val(res.url);\n                                    } else {\n                                        layer.msg(res.msg, {icon: 5});\n                                    }\n                                }\n                            });\n[/pre]\n[hr]\n测试上传图片\nimg[http://oih7sazbd.bkt.clouddn.com/Fvb7JPJkVnaPNWUb9m35ZIE56kCd] \n[hr]\n测试表情\nface[微笑] face[嘻嘻] face[哈哈] face[可爱] face[可怜] \n[hr]\n测试超链接\n a(https://github.com/lyusantu)[https://github.com/lyusantu] ', '20', '0', '2017-12-21 10:49:20', '1', '0', '0', '0', '0', '0');
INSERT INTO `post` VALUES ('19', '啊啊', '1', '啊', '80', '1', '2017-12-21 10:53:28', '1', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `sign`
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sign
-- ----------------------------
INSERT INTO `sign` VALUES ('40', '1', '2015-12-20 11:14:20', '2017-12-21 10:34:22');

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
  `type` varchar(255) DEFAULT NULL,
  `active_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '352050321@qq.com', '超人', '9cbf8a4dcb8e30682b927f352d6559a0', '99694', 'http://oih7sazbd.bkt.clouddn.com/FivibFbr6k8GqJBdZBea56zq0S4X', '0', '', '2017-12-21 10:53:27', '深圳在哪里呀', '0', 'eazy作者', '9', 'admin', null);

-- ----------------------------
-- Table structure for `verify`
-- ----------------------------
DROP TABLE IF EXISTS `verify`;
CREATE TABLE `verify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `answer` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of verify
-- ----------------------------
INSERT INTO `verify` VALUES ('1', '请输入so eazy', 'so eazy');
INSERT INTO `verify` VALUES ('2', '2 + 2 * 2 = ?', '6');
INSERT INTO `verify` VALUES ('3', '12月25日是什么节日？', '圣诞节');
INSERT INTO `verify` VALUES ('4', '中国的首都是哪座城市？', '北京');
INSERT INTO `verify` VALUES ('5', 'Java和JavaScript有关系吗', '没关系');
