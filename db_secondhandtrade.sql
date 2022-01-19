/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.24 : Database - db_secondhandtrade
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_secondhandtrade` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_secondhandtrade`;

/*Table structure for table `t_announcement` */

DROP TABLE IF EXISTS `t_announcement`;

CREATE TABLE `t_announcement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `time` datetime DEFAULT NULL COMMENT '发布时间',
  `click` int(11) DEFAULT NULL COMMENT '点击数',
  `sortNum` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `t_announcement` */

insert  into `t_announcement`(`id`,`title`,`content`,`time`,`click`,`sortNum`) values (3,'公告1','<p>公告1</p>\r\n\r\n<p><img alt=\"\" src=\"/static/images/articleImage/202201151617541642234674993.jpg\" style=\"height:500px; width:888px\" /></p>\r\n','2022-01-15 14:35:09',74,1),(4,'公告2','<p>公告2</p>\r\n\r\n<p><img alt=\"\" src=\"/static/images/articleImage/202201151617021642234622557.jpg\" style=\"height:500px; width:888px\" /></p>\r\n','2022-01-15 14:35:22',20,2),(5,'公告3','<p>公告3</p>\r\n\r\n<p><img alt=\"\" src=\"/static/images/articleImage/202201151618291642234709015.jpg\" style=\"height:500px; width:888px\" /></p>\r\n','2022-01-15 16:18:33',31,3);

/*Table structure for table `t_carousel` */

DROP TABLE IF EXISTS `t_carousel`;

CREATE TABLE `t_carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `imageName` varchar(100) DEFAULT NULL COMMENT '图片名称',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` varchar(200) DEFAULT NULL COMMENT '描述',
  `sortNum` int(11) DEFAULT NULL COMMENT '排列顺序',
  `url` varchar(100) DEFAULT NULL COMMENT '链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `t_carousel` */

insert  into `t_carousel`(`id`,`imageName`,`title`,`content`,`sortNum`,`url`) values (21,'202201150019141642177154078.jpg','公告1','公告1公告1公告1',1,'/announcement/3'),(22,'202201160201071642269667826.jpg','百草味果干大礼包','百草味果干大礼包零食年货芒果干送女生礼盒蜜饯果脯小吃食品休闲',2,'/goods/22'),(23,'202201150019441642177184535.jpg','求购一个机械键盘','求购一个机械键盘.200元以内',3,'/goods/31');

/*Table structure for table `t_contact` */

DROP TABLE IF EXISTS `t_contact`;

CREATE TABLE `t_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `content` varchar(1000) DEFAULT NULL COMMENT '留言内容',
  `reply` varchar(1000) DEFAULT NULL COMMENT '答复',
  `time` datetime DEFAULT NULL COMMENT '留言时间',
  `userId` int(11) DEFAULT NULL COMMENT '留言用户id（普通用户）',
  `userIdReply` int(11) DEFAULT NULL COMMENT '回复用户id（管理员）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `t_contact` */

insert  into `t_contact`(`id`,`content`,`reply`,`time`,`userId`,`userIdReply`) values (8,'内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容','1111111111111','2022-01-12 14:39:53',56,1),(18,'1','1231231321','2022-01-12 16:12:30',56,1),(19,'2','00000','2022-01-12 16:12:34',56,1),(21,'3',NULL,'2022-01-12 18:00:57',2,NULL);

/*Table structure for table `t_contact_information` */

DROP TABLE IF EXISTS `t_contact_information`;

CREATE TABLE `t_contact_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `content` varchar(100) DEFAULT NULL COMMENT '内容',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `t_contact_information` */

insert  into `t_contact_information`(`id`,`name`,`content`,`userId`) values (1,'QQ','1203007466',56),(2,'微信','ledao303',56),(5,'手机','13667832012',56),(21,'微信','123434243',3),(22,'微信','2a231',2);

/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `content` text COMMENT '商品详情',
  `priceNow` decimal(10,0) DEFAULT NULL COMMENT '现在价格',
  `priceLast` decimal(10,0) DEFAULT NULL COMMENT '上次价格',
  `state` int(11) DEFAULT NULL COMMENT '商品状态,0为未审核,1为上架中,2为审核不通过,3为已下架,4为被预定,5为交易成功',
  `reason` varchar(500) DEFAULT NULL COMMENT '审核不通过的理由',
  `goodsTypeId` int(11) DEFAULT NULL COMMENT '商品类别id',
  `userId` int(11) DEFAULT NULL COMMENT '发布者id',
  `addTime` datetime DEFAULT NULL COMMENT '添加时间',
  `isRecommend` int(11) DEFAULT NULL COMMENT '是否推荐,0为不推荐(默认),1为推荐',
  `recommendTime` datetime DEFAULT NULL COMMENT '推荐时间',
  `recommendDays` int(11) DEFAULT NULL COMMENT '推荐天数',
  `click` int(11) DEFAULT NULL COMMENT '点击数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

/*Data for the table `t_goods` */

insert  into `t_goods`(`id`,`name`,`content`,`priceNow`,`priceLast`,`state`,`reason`,`goodsTypeId`,`userId`,`addTime`,`isRecommend`,`recommendTime`,`recommendDays`,`click`) values (21,'北美电器（ACA）电风扇 一体式折叠风扇台式两用伸缩式落地扇','<p><img alt=\"\" src=\"/static/images/articleImage/202201160115131642266913331.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','333','0',1,NULL,5,56,'2022-01-16 01:15:23',0,NULL,NULL,2),(22,'百草味果干大礼包零食年货芒果干送女生礼盒蜜饯果脯小吃食品休闲','<p><img alt=\"\" src=\"/static/images/articleImage/202201160117501642267070232.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','99','0',0,NULL,4,56,'2022-01-16 01:17:57',0,NULL,NULL,26),(23,'旺旺旺仔小馒头30袋原味牛奶味儿童饼干怀旧零食品小吃整箱批发','<p><img alt=\"\" src=\"/static/images/articleImage/202201160119351642267175196.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','15','0',0,NULL,4,56,'2022-01-16 01:19:40',1,NULL,NULL,55),(24,'加绒加厚保暖上衣v领黑色打底衫女秋冬内搭长袖t恤时尚鸡心领百搭','<p><img alt=\"\" src=\"/static/images/articleImage/202201160121371642267297550.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','30','33',1,NULL,2,56,'2022-01-16 01:21:42',1,NULL,NULL,55),(25,'唐狮2022年新款白色长袖打底衫女蕾丝高领内搭设计感时尚洋气上衣','<p><img alt=\"\" src=\"/static/images/articleImage/202201160122491642267369440.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','60','0',1,NULL,2,56,'2022-01-16 01:22:54',0,NULL,NULL,17),(26,'优衣库 秋冬 男装 摇粒绒半拉链套头衫(长袖保暖抓绒) 441257','<p><img alt=\"\" src=\"/static/images/articleImage/202201160124071642267447440.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','70','0',1,NULL,2,56,'2022-01-16 01:24:12',1,NULL,NULL,78),(27,'畅享20Plus 8+128G 6.63英寸4800W华为5g手机','<p><img alt=\"\" src=\"/static/images/articleImage/202201160125321642267532402.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','999','0',1,NULL,1,56,'2022-01-16 01:25:46',1,NULL,NULL,214),(28,'三星A52千元学生5G智能数码手机Samsung Galaxy','<p><img alt=\"\" src=\"/static/images/articleImage/202201160126481642267608246.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','1888','0',1,NULL,1,56,'2022-01-16 01:26:59',1,'2022-01-19 15:20:55',1,97),(29,'超薄办公一体机电脑高端四核i3i5i7家用网吧吃鸡游戏型独显台式主机19-27英寸非触摸屏高配整机','<p><img alt=\"\" src=\"/static/images/articleImage/202201160128131642267693062.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','1222','0',1,NULL,1,56,'2022-01-16 01:28:17',0,NULL,NULL,14),(30,'22寸壁挂嵌入式工业工控平板电脑一体机电容电阻触摸屏显示器安卓触控查询plc全封闭','<p><img alt=\"\" src=\"/static/images/articleImage/202201160130111642267811613.jpg\" style=\"height:375px; width:666px\" /></p>\r\n','999','0',1,NULL,1,56,'2022-01-16 01:30:16',1,'2022-01-19 23:43:53',2,23),(31,'求购一个机械键盘','<p>200元以内</p>\r\n','88','0',1,NULL,8,56,'2022-01-16 02:05:02',0,'2022-01-18 16:25:40',10,45),(32,'22222','<p>222222222</p>\r\n','200','0',2,'哈哈',8,56,'2022-01-16 02:21:17',0,NULL,NULL,20),(33,'2','<p>2</p>\r\n','20','0',2,'99999',2,56,'2022-01-16 21:39:20',0,'2022-01-18 16:24:02',10,28),(38,'222','<p>222</p>\r\n','22','0',1,NULL,1,2,'2022-01-18 12:03:22',0,NULL,NULL,7);

/*Table structure for table `t_goods_type` */

DROP TABLE IF EXISTS `t_goods_type`;

CREATE TABLE `t_goods_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `sortNum` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `t_goods_type` */

insert  into `t_goods_type`(`id`,`name`,`sortNum`) values (1,'电子产品',1),(2,'服饰',2),(3,'书籍',3),(4,'食品',4),(5,'生活用品',5),(6,'运动器械',6),(7,'代步工具',7),(8,'求购',8);

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` int(11) DEFAULT NULL COMMENT '接收人id',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `time` datetime DEFAULT NULL COMMENT '添加时间',
  `isRead` int(11) DEFAULT NULL COMMENT '是否已读,0表未读,1表示已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `t_message` */

insert  into `t_message`(`id`,`userId`,`content`,`time`,`isRead`) values (6,56,'你的商品（优衣库 秋冬 男装 摇粒绒半拉链套头衫(长袖保暖抓绒) 441257）已被预定，请联系买家当面交易哦！！','2022-01-18 14:57:32',0),(7,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预定，请联系买家当面交易哦！！','2022-01-18 17:11:39',0),(8,56,'你的商品（加绒加厚保暖上衣v领黑色打底衫女秋冬内搭长袖t恤时尚鸡心领百搭）已被预定，请联系买家当面交易哦！！','2022-01-18 17:13:55',0),(9,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预定，请联系买家当面交易哦！！','2022-01-18 17:16:25',0),(10,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预定，请联系买家当面交易哦！！','2022-01-18 17:18:26',0),(11,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预定，请联系买家当面交易哦！！','2022-01-18 17:18:59',0),(12,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预定，请联系买家当面交易哦！！','2022-01-18 22:16:58',0),(13,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预定，请联系买家当面交易哦！！','2022-01-18 22:21:13',0),(14,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预订，请联系买家当面交易哦！！','2022-01-18 23:53:04',0),(15,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被预订，请联系买家当面交易哦！！','2022-01-18 23:53:43',0),(16,56,'你的商品（畅享20Plus 8+128G 6.63英寸4800W华为5g手机）已被卖家取消预订！！','2022-01-18 23:56:32',0),(17,56,'你的商品（三星A52千元学生5G智能数码手机Samsung Galaxy）已被预订，请联系买家当面交易哦！！','2022-01-18 23:57:49',0),(18,2,'你预订的商品（三星A52千元学生5G智能数码手机Samsung Galaxy）已被卖家取消预订！！','2022-01-19 00:03:45',0);

/*Table structure for table `t_reserve_record` */

DROP TABLE IF EXISTS `t_reserve_record`;

CREATE TABLE `t_reserve_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `goodsId` int(11) DEFAULT NULL COMMENT '商品id',
  `userId` int(11) DEFAULT NULL COMMENT '支付人id',
  `reserveTime` datetime DEFAULT NULL COMMENT '支付时间',
  `state` int(11) DEFAULT NULL COMMENT '是否取消,0代表未取消,1代表已取消',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `t_reserve_record` */

insert  into `t_reserve_record`(`id`,`goodsId`,`userId`,`reserveTime`,`state`) values (22,27,2,'2022-01-18 22:16:58',1),(23,27,2,'2022-01-18 22:21:13',1),(24,27,2,'2022-01-18 23:53:04',1),(25,27,2,'2022-01-18 23:53:43',1),(26,28,2,'2022-01-18 23:57:49',1);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `nickName` varchar(50) DEFAULT NULL COMMENT '昵称',
  `type` int(11) DEFAULT NULL COMMENT '1代表管理员,2代表普通用户',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` int(11) DEFAULT NULL COMMENT '1代表正常,2代表封禁',
  `imageName` varchar(50) DEFAULT NULL COMMENT '用户头像图片名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`password`,`nickName`,`type`,`email`,`status`,`imageName`) values (1,'admin','admin','乐道',1,'12030074691@qq.com',1,NULL),(2,'2','2','tom',2,'123@qq.com',1,'202201121805431641981943150.jpg'),(3,'3','3','jack',2,'1231@qq.com',0,'202201130053431642006423417.jpg'),(56,'1','1','哈哈',2,'1203007469@qq.com',1,'202201131312271642050747950.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
