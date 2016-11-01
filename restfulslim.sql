-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: restfulslim
-- ------------------------------------------------------
-- Server version	5.5.38-0ubuntu0.12.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `acc_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(32) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `birthday` date NOT NULL,
  `gender` enum('Male','Female') DEFAULT NULL,
  `nickname` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `activated` enum('Yes','No') NOT NULL DEFAULT 'No',
  `email` varchar(255) NOT NULL,
  `email_canonical` varchar(255) NOT NULL,
  `mobile_phone` varchar(16) NOT NULL,
  `location_iso2` varchar(2) NOT NULL,
  `timezone` varchar(64) NOT NULL,
  `lang_iso2` varchar(2) NOT NULL,
  `algorithm` varchar(16) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `last_logged_at` datetime DEFAULT NULL,
  `last_logged_ip` varchar(64) NOT NULL,
  `role` varchar(32) NOT NULL,
  `locked` enum('Yes','No') NOT NULL DEFAULT 'No',
  `lock_reason` varchar(255) NOT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `activation_expires_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deletion_requested_at` datetime DEFAULT NULL,
  `deletion_scheduled_at` datetime DEFAULT NULL,
  `deleted` enum('Yes','No') NOT NULL DEFAULT 'No',
  PRIMARY KEY (`acc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'John','Doe','1982-01-01','Male','John D.','admin','Yes','admin@joodee.org','admin@joodee.org','+111111111111','md','Europe/Chisinau','en','sha512','6b43c0090110623ed951a9a95c764116','a6da32f842e095e7a100e416135a9871a348af9b4182775fba5e19e981224baead9a1a27499dffaef4283a7cf4bc2d96c2389ccbe48d6a7dad247518def227e5','b8b13c64de28119b030cd33f9cc8b398ef1287f3291221e7cb8a38dac879074e','2014-09-02 07:34:43','127.0.0.1','admin','No','','2014-08-12 21:34:39','2013-11-03 18:58:29','2013-10-31 18:58:29','2014-08-12 20:28:17',NULL,NULL,'No');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_api_keys`
--

DROP TABLE IF EXISTS `account_api_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_api_keys` (
  `key_id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_id` int(11) NOT NULL,
  `key_secret` varchar(128) NOT NULL,
  `key_created_at` datetime NOT NULL,
  `key_updated_at` datetime NOT NULL,
  `key_status` enum('On','Off','Block') NOT NULL DEFAULT 'On',
  PRIMARY KEY (`key_id`),
  UNIQUE KEY `key_secret` (`key_secret`),
  KEY `acc_id` (`acc_id`),
  CONSTRAINT `account_api_keys_ibfk_1` FOREIGN KEY (`acc_id`) REFERENCES `account` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_api_keys`
--

LOCK TABLES `account_api_keys` WRITE;
/*!40000 ALTER TABLE `account_api_keys` DISABLE KEYS */;
INSERT INTO `account_api_keys` VALUES (1,1,'6987565dd6e61f7d0a61e6c427994e68d83aea4e596f2f8c9ee87653bf26bcd3','2014-04-13 02:09:20','2014-04-13 02:09:20','On');
/*!40000 ALTER TABLE `account_api_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `art_id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_id` int(11) DEFAULT NULL,
  `art_title` varchar(255) NOT NULL,
  `art_body` text NOT NULL,
  `art_created_at` datetime NOT NULL,
  `art_updated_at` datetime NOT NULL,
  PRIMARY KEY (`art_id`),
  KEY `acc_id` (`acc_id`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`acc_id`) REFERENCES `account` (`acc_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (5,1,'Test Article','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.','2014-09-13 04:45:44','2014-09-13 04:45:44'),(6,1,'Test Article','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.','2014-09-13 04:47:33','2014-09-13 04:47:33'),(7,1,'Test Article','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.','2014-09-13 04:48:58','2014-09-13 04:48:58');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-15  6:39:50
