/*
MySQL Data Transfer
Source Host: localhost
Source Database: android_order
Target Host: localhost
Target Database: android_order
Date: 2018/12/14 22:34:06
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `id` int(10) NOT NULL auto_increment,
  `userid` int(10) default NULL,
  `foodid` int(10) default NULL,
  `count` int(10) default '0',
  `isbuy` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(10) NOT NULL auto_increment,
  `foodname` varchar(32) default NULL,
  `type` int(10) default NULL,
  `prize` varchar(10) default NULL,
  `sales` int(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `ordername` varchar(10) default NULL,
  `id` int(10) NOT NULL auto_increment,
  `userid` int(10) default NULL,
  `foodids` varchar(255) default NULL,
  `prizesum` varchar(32) default NULL,
  `statue` varchar(32) default NULL,
  `time` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` int(10) NOT NULL auto_increment,
  `typename` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL auto_increment,
  `username` varchar(32) default NULL,
  `sex` varchar(2) default NULL,
  `age` varchar(3) default NULL,
  `role` varchar(10) default NULL,
  `other` varchar(255) default NULL,
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `cart` VALUES ('3', '1', '1', '4', '0');
INSERT INTO `cart` VALUES ('4', '1', '5', '2', '0');
INSERT INTO `cart` VALUES ('5', '1', '3', '3', '0');
INSERT INTO `cart` VALUES ('6', '1', '2', '2', '0');
INSERT INTO `menu` VALUES ('1', '红烧肉', '1', '50', '300');
INSERT INTO `menu` VALUES ('2', '米饭', '3', '10', '100');
INSERT INTO `menu` VALUES ('3', '糖醋里脊', '1', '60', '200');
INSERT INTO `menu` VALUES ('4', '黄瓜凉皮', '2', '20', '103');
INSERT INTO `menu` VALUES ('5', '哈尔滨啤酒', '4', '10', '200');
INSERT INTO `menu` VALUES ('6', '百事可乐', '4', '10', '50');
INSERT INTO `orders` VALUES ('红烧肉', '1', '1', '123', '300', '烹饪', '2018');
INSERT INTO `type` VALUES ('1', '热菜');
INSERT INTO `type` VALUES ('2', '凉菜');
INSERT INTO `type` VALUES ('3', '主食');
INSERT INTO `type` VALUES ('4', '饮料');
INSERT INTO `user` VALUES ('1', 'zzg', null, null, null, null, '123');
