/*
SQLyog v10.2 
MySQL - 5.7.21 : Database - ds0
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ds0` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ds0`;

/*Table structure for table `t_order_0` */

DROP TABLE IF EXISTS `t_order_0`;

CREATE TABLE `t_order_0` (
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) NOT NULL,
  `status` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_order_0` */

insert  into `t_order_0`(`user_id`,`order_id`,`status`) values (0,18072719332400002,585),(0,18072719335100004,585),(0,18072719335100006,585),(0,18072719335200008,585),(0,18072719335200010,585),(0,18072719335200012,585),(0,18082316421000002,767);

/*Table structure for table `t_order_1` */

DROP TABLE IF EXISTS `t_order_1`;

CREATE TABLE `t_order_1` (
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) NOT NULL,
  `status` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_order_1` */

insert  into `t_order_1`(`user_id`,`order_id`,`status`) values (0,18072719332300001,585),(0,18072719332600003,585),(0,18072719335100005,585),(0,18072719335200007,585),(0,18072719335200009,585),(0,18072719335200011,585),(0,18082316420700001,767),(40,18082316422800003,432432432);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
