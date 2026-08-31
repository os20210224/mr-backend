/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 10.4.32-MariaDB : Database - mr-projekat
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mr-projekat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `mr-projekat`;

/*Table structure for table `Friend` */

DROP TABLE IF EXISTS `Friend`;

CREATE TABLE `Friend` (
  `user` bigint(20) NOT NULL CHECK (`user` < `friend`),
  `friend` bigint(20) NOT NULL CHECK (`friend` > `user`),
  `status` enum('pending','friends') NOT NULL,
  `requested_by` bigint(20) NOT NULL,
  PRIMARY KEY (`user`,`friend`),
  KEY `friend_fk_friend` (`friend`),
  CONSTRAINT `friend_fk_friend` FOREIGN KEY (`friend`) REFERENCES `User` (`idUser`) ON DELETE CASCADE,
  CONSTRAINT `friend_fk_user` FOREIGN KEY (`user`) REFERENCES `User` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `Friend` */

insert  into `Friend`(`user`,`friend`,`status`,`requested_by`) values 
(1,2,'friends',1);

/*Table structure for table `Throw` */

DROP TABLE IF EXISTS `Throw`;

CREATE TABLE `Throw` (
  `idThrow` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `score` float(10,2) NOT NULL,
  `user` bigint(20) NOT NULL,
  PRIMARY KEY (`idThrow`),
  KEY `score_fk_user` (`user`),
  CONSTRAINT `score_fk_user` FOREIGN KEY (`user`) REFERENCES `User` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `Throw` */

insert  into `Throw`(`idThrow`,`date`,`score`,`user`) values 
(2,'2026-08-22 16:58:45',11.40,1),
(4,'2026-08-24 20:43:40',1.30,2),
(6,'2026-08-28 17:57:22',0.70,4);

/*Table structure for table `User` */

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `idUser` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(512) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `unique_username` (`username`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `User` */

insert  into `User`(`idUser`,`name`,`username`,`password`,`email`) values 
(1,'Mau Maunović Ulica Mau','mau','mau','mau@maunovic@uluca.mau'),
(2,'Mina Minić Ulica Mina','mina','mina','mina@minic@ulica.mina'),
(4,'Joca Kormilo','jk17','pihtije','joca.kormilo@ukleti.holandjanin'),
(14,'api test','test','test','test@test.test');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
