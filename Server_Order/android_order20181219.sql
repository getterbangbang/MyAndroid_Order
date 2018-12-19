/*
MySQL Data Transfer
Source Host: localhost
Source Database: android_order
Target Host: localhost
Target Database: android_order
Date: 2018/12/19 11:18:23
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

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
  `id` int(10) NOT NULL auto_increment,
  `ordername` varchar(10) default NULL,
  `userid` int(10) default NULL,
  `orderids` varchar(255) default NULL,
  `prizesum` varchar(32) default NULL,
  `statue` varchar(32) default NULL,
  `time` varchar(32) default NULL,
  `tableid` int(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for table_
-- ----------------------------
DROP TABLE IF EXISTS `table_`;
CREATE TABLE `table_` (
  `id` int(10) NOT NULL auto_increment,
  `tablenum` int(10) default NULL,
  `isempty` int(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `cart` VALUES ('3', '1', '1', '4', '1');
INSERT INTO `cart` VALUES ('4', '1', '5', '2', '1');
INSERT INTO `cart` VALUES ('5', '1', '3', '5', '1');
INSERT INTO `cart` VALUES ('6', '1', '2', '2', '1');
INSERT INTO `cart` VALUES ('7', '1', '1', '2', '1');
INSERT INTO `cart` VALUES ('8', '1', '5', '4', '1');
INSERT INTO `cart` VALUES ('9', '1', '1', '1', '1');
INSERT INTO `cart` VALUES ('10', '1', '3', '1', '1');
INSERT INTO `cart` VALUES ('11', '1', '1', '2', '1');
INSERT INTO `cart` VALUES ('12', '1', '1', '2', '1');
INSERT INTO `cart` VALUES ('13', '1', '1', '1', '1');
INSERT INTO `cart` VALUES ('14', '1', '3', '2', '1');
INSERT INTO `cart` VALUES ('15', '1', '1', '2', '1');
INSERT INTO `cart` VALUES ('16', '1', '3', '1', '1');
INSERT INTO `cart` VALUES ('17', '1', '6', '1', '1');
INSERT INTO `cart` VALUES ('18', '1', '5', '3', '1');
INSERT INTO `cart` VALUES ('19', '1', '2', '1', '1');
INSERT INTO `cart` VALUES ('20', '1', '1', '1', '1');
INSERT INTO `cart` VALUES ('21', '1', '3', '1', '1');
INSERT INTO `cart` VALUES ('22', '1', '5', '1', '1');
INSERT INTO `cart` VALUES ('23', '1', '3', '2', '1');
INSERT INTO `cart` VALUES ('24', '1', '1', '1', '1');
INSERT INTO `cart` VALUES ('25', '1', '1', '1', '1');
INSERT INTO `cart` VALUES ('26', '1', '5', '1', '1');
INSERT INTO `cart` VALUES ('27', '1', '1', '3', '1');
INSERT INTO `cart` VALUES ('28', '1', '5', '1', '1');
INSERT INTO `cart` VALUES ('29', '1', '3', '1', '1');
INSERT INTO `cart` VALUES ('30', '1', '3', '1', '1');
INSERT INTO `cart` VALUES ('31', '1', '5', '1', '1');
INSERT INTO `cart` VALUES ('32', '1', '4', '5', '1');
INSERT INTO `cart` VALUES ('33', '1', '2', '4', '1');
INSERT INTO `cart` VALUES ('34', '1', '5', '3', '1');
INSERT INTO `cart` VALUES ('35', '1', '3', '2', '1');
INSERT INTO `cart` VALUES ('36', '1', '2', '2', '1');
INSERT INTO `cart` VALUES ('37', '1', '3', '2', '1');
INSERT INTO `cart` VALUES ('38', '1', '1', '2', '0');
INSERT INTO `cart` VALUES ('39', '1', '3', '9', '0');
INSERT INTO `menu` VALUES ('1', '红烧肉', '1', '50', '300');
INSERT INTO `menu` VALUES ('2', '米饭', '3', '10', '100');
INSERT INTO `menu` VALUES ('3', '糖醋里脊', '1', '60', '200');
INSERT INTO `menu` VALUES ('4', '黄瓜凉皮', '2', '20', '103');
INSERT INTO `menu` VALUES ('5', '哈尔滨啤酒', '4', '10', '200');
INSERT INTO `menu` VALUES ('6', '百事可乐', '4', '10', '50');
INSERT INTO `orders` VALUES ('1', '红烧肉', '1', '123', '300', '烹饪', '2018', '2');
INSERT INTO `orders` VALUES ('2', '红烧肉等', '1', '3,4,5,6,', '420', '已提交', '2018-12-15 20:55', '2');
INSERT INTO `orders` VALUES ('3', '红烧肉等', '1', '3,4,5,6,', '560', '已提交', '2018-12-15 20:59', '2');
INSERT INTO `orders` VALUES ('4', '红烧肉等', '1', '3,4,5,6,', '540', '已提交', '2018-12-15 21:00', '2');
INSERT INTO `orders` VALUES ('5', '红烧肉等', '1', '7,', '100', '已提交', '2018-12-15 21:05', '2');
INSERT INTO `orders` VALUES ('6', '红烧肉等', '1', '9,10,8,', '150', '已提交', '2018-12-17 21:57', '2');
INSERT INTO `orders` VALUES ('7', '红烧肉等', '1', '9,10,8,', '150', '已提交', '2018-12-17 21:57', '3');
INSERT INTO `orders` VALUES ('8', '红烧肉等', '1', '11,', '100', '已提交', '2018-12-17 23:45', '2');
INSERT INTO `orders` VALUES ('9', '红烧肉等', '1', '11,', '100', '已提交', '2018-12-17 23:45', '2');
INSERT INTO `orders` VALUES ('10', '红烧肉等', '1', '12,13,14,', '270', '已提交', '2018-12-17 23:46', '2');
INSERT INTO `orders` VALUES ('11', '红烧肉等', '1', '12,13,14,', '270', '已提交', '2018-12-17 23:47', '2');
INSERT INTO `orders` VALUES ('12', '红烧肉等', '1', '15,16,', '160', '已提交', '2018-12-18 16:02', '2');
INSERT INTO `orders` VALUES ('13', '红烧肉等', '1', '15,16,', '160', '已提交', '2018-12-18 16:02', '2');
INSERT INTO `orders` VALUES ('14', '米饭等', '1', '19,18,17,', '50', '已提交', '2018-12-18 21:02', '2');
INSERT INTO `orders` VALUES ('15', '米饭等', '1', '19,18,17,', '50', '已提交', '2018-12-18 21:02', '2');
INSERT INTO `orders` VALUES ('16', '红烧肉等', '1', '20,21,22,', '120', '已提交', '2018-12-18 21:04', '2');
INSERT INTO `orders` VALUES ('17', '红烧肉等', '1', '24,23,', '170', '已提交', '2018-12-18 21:06', '2');
INSERT INTO `orders` VALUES ('18', '红烧肉等', '1', '25,26,', '60', '已提交', '2018-12-18 21:08', '2');
INSERT INTO `orders` VALUES ('19', '红烧肉等', '1', '27,29,28,', '220', '已提交', '2018-12-18 21:12', '2');
INSERT INTO `orders` VALUES ('20', '糖醋里脊等', '1', '30,', '60', '已提交', '2018-12-18 21:15', '2');
INSERT INTO `orders` VALUES ('21', '哈尔滨啤酒等', '1', '31,', '10', '已提交', '2018-12-18 21:23', '2');
INSERT INTO `orders` VALUES ('22', '黄瓜凉皮等', '1', '32,', '100', '已提交', '2018-12-18 21:24', '2');
INSERT INTO `orders` VALUES ('23', '米饭等', '1', '33,', '40', '已提交', '2018-12-18 21:26', '2');
INSERT INTO `orders` VALUES ('24', '哈尔滨啤酒等', '1', '34,', '30', '已提交', '2018-12-18 21:32', '2');
INSERT INTO `orders` VALUES ('25', '糖醋里脊等', '1', '35,', '120', '已提交', '2018-12-18 21:33', '3');
INSERT INTO `orders` VALUES ('26', '米饭等', '1', '36,', '20', '已提交', '2018-12-18 21:33', '3');
INSERT INTO `orders` VALUES ('27', '糖醋里脊等', '1', '37,', '120', '已提交', '2018-12-18 21:34', '3');
INSERT INTO `table_` VALUES ('1', '1', '1');
INSERT INTO `table_` VALUES ('2', '2', '0');
INSERT INTO `table_` VALUES ('3', '3', '0');
INSERT INTO `type` VALUES ('1', '热菜');
INSERT INTO `type` VALUES ('2', '凉菜');
INSERT INTO `type` VALUES ('3', '主食');
INSERT INTO `type` VALUES ('4', '饮料');
INSERT INTO `user` VALUES ('1', 'zzg', null, null, 'customer', null, '123');
INSERT INTO `user` VALUES ('2', 'admin', null, null, 'worker', null, '123');
