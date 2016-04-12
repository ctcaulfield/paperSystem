-- MySQL dump 10.13  Distrib 5.5.15, for Win32 (x86)
--
-- Host: localhost    Database: pe1
-- ------------------------------------------------------
-- Server version	5.1.59-community

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

DROP DATABASE IF EXISTS travel;
CREATE DATABASE travel;
USE travel;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment` (
  `EquipID` int(11) NOT NULL DEFAULT '0',
  `EquipmentName` varchar(50) NOT NULL DEFAULT '',
  `EquipmentDescription` varchar(100) NOT NULL DEFAULT '',
  `EquipmentCapacity` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`EquipID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (568,'Continental','Passenger and Cargo',400),(894,'Bus 264','Coach',35),(1256,'Airbus 300','Long Range',150),(3644,'Boeing 767','Short Range',100),(5634,'Boeing 727','Short Range',100),(7624,'Bus 345','Coach',35),(8596,'Boeing 727','Short Range',100);
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locations` (
  `LocationCode` char(3) NOT NULL DEFAULT '',
  `Location` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`LocationCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES ('BOS','Boston'),('BUF','Buffalo'),('BUR','Burbank'),('JFK','New York'),('LAS','Las Vegas'),('LGA','New York'),('NAS','Nassau'),('ROC','Rochester');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passenger` (
  `PassengerID` int(11) NOT NULL DEFAULT '0',
  `FName` varchar(50) NOT NULL DEFAULT '',
  `LName` varchar(50) NOT NULL DEFAULT '',
  `Street` varchar(50) NOT NULL DEFAULT '',
  `Zip` varchar(5) NOT NULL DEFAULT '',
  PRIMARY KEY (`PassengerID`),
  KEY `Zip` (`Zip`),
  CONSTRAINT `passenger_ibfk_1` FOREIGN KEY (`Zip`) REFERENCES `zips` (`Zip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
INSERT INTO `passenger` VALUES (1,'Ken','Bennet','12 Marway Circle','14624'),(2,'Patti','Hughes','280 Commerce Dr','14623'),(3,'Dale','Payne','34 Foley Dr','14551'),(4,'Dan','Callahan','320 West Craig Hill','14626'),(5,'Rich','Gleason','232 Industrial Park Dr','13340'),(6,'Scott','Kier','150 Highland Ave.','14618'),(7,'Mark','Lucas','425 Old Center Macedon Rd','14450'),(8,'Scott','Wilson','70 Bermar Park','14624'),(9,'Terry','Brown','100 Pennsylvania Ave','01701'),(10,'Curtis','Brown','100 Ajax Rd','14624');
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phones`
--

DROP TABLE IF EXISTS `phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phones` (
  `PassengerId` int(11) NOT NULL DEFAULT '0',
  `PhoneNum` varchar(15) NOT NULL DEFAULT '',
  `PhoneType` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`PassengerId`,`PhoneNum`),
  CONSTRAINT `phones_ibfk_1` FOREIGN KEY (`PassengerId`) REFERENCES `passenger` (`PassengerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phones`
--

LOCK TABLES `phones` WRITE;
/*!40000 ALTER TABLE `phones` DISABLE KEYS */;
INSERT INTO `phones` VALUES (1,'585-475-1440','Home'),(2,'585-874-4956','Home'),(3,'585-325-6530','Cell'),(3,'585-454-3290','Home'),(4,'585-254-8080','Home'),(5,'585-442-0450','Home'),(6,'585-461-6898','Home'),(7,'585-264-3135','Home'),(8,'585-463-3420','Cell'),(8,'585-538-6822','Home'),(9,'612-576-9985','Home'),(10,'585-263-3905','Cell'),(10,'585-593-5860','Home');
/*!40000 ALTER TABLE `phones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `Date` date NOT NULL DEFAULT '0000-00-00',
  `TripNum` varchar(10) NOT NULL DEFAULT '0',
  `Role` varchar(20) NOT NULL DEFAULT '',
  `Name` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`Date`,`TripNum`,`Role`),
  KEY `TripNum` (`TripNum`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`TripNum`) REFERENCES `trip` (`TripNum`),
  CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`Date`) REFERENCES `trip` (`Date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('2015-08-14','3030','Pilot','Greg Zalewski'),('2015-09-07','546','Driver','Brian Page'),('2015-09-11','4567','Engineer','Howard Vogel'),('2015-10-10','3030','CoPilot','Dan Gnagy'),('2015-10-10','3030','Pilot','Brad Raushey'),('2015-10-11','1027','CoPilot','Lorraine LeBan'),('2015-10-11','1027','Pilot','Molly Connor'),('2015-11-04','6432','Driver','Pam Stewart');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip` (
  `Date` date NOT NULL DEFAULT '0000-00-00',
  `TripNum` varchar(10) NOT NULL DEFAULT '',
  `ArrivalTime` varchar(10) DEFAULT '',
  `ArrivalLocCode` char(3) DEFAULT '',
  `DepartureTime` varchar(10) DEFAULT '',
  `DepartureLocCode` char(3) DEFAULT '',
  `EstArrivalTime` varchar(10) DEFAULT '',
  `EstDepartureTime` varchar(10) DEFAULT '',
  `EquipId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Date`,`TripNum`),
  KEY `Date` (`Date`),
  KEY `TripNum` (`TripNum`),
  KEY `ArrivalLocCode` (`ArrivalLocCode`),
  KEY `DepartureLocCode` (`DepartureLocCode`),
  KEY `EquipId` (`EquipId`),
  CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`TripNum`) REFERENCES `trip_directory` (`TripNum`),
  CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`ArrivalLocCode`) REFERENCES `locations` (`LocationCode`),
  CONSTRAINT `trip_ibfk_3` FOREIGN KEY (`DepartureLocCode`) REFERENCES `locations` (`LocationCode`),
  CONSTRAINT `trip_ibfk_4` FOREIGN KEY (`EquipId`) REFERENCES `equipment` (`EquipID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
INSERT INTO `trip` VALUES ('2015-08-14','3030','2:15 PM','NAS','1:00 PM','BOS','2:00 PM','1:00 PM',8596),('2015-09-07','546','11:45 PM','ROC','3:00 PM','JFK','11:30 PM','3:00 PM',894),('2015-09-11','4567','8:30 AM','BUF','6:00 AM','ROC','7:30 AM','7:15 AM',568),('2015-10-10','3030',NULL,'NAS','1:00 PM','BOS','2:00 PM','1:00 PM',5634),('2015-10-11','1027',NULL,NULL,'10:00 AM','BUF','2:00 PM','10:00 AM',1256),('2015-11-04','6432',NULL,NULL,'1:00 PM','JFK','8:00 PM','1:00 PM',7624);
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_directory`
--

DROP TABLE IF EXISTS `trip_directory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_directory` (
  `TripNum` varchar(10) NOT NULL DEFAULT '',
  `TripType` char(1) NOT NULL DEFAULT '',
  `ArrivalTime` varchar(10) NOT NULL DEFAULT '',
  `ArrivalLocCode` char(3) NOT NULL DEFAULT '',
  `DepartureTime` varchar(10) NOT NULL DEFAULT '',
  `DepartureLocCode` char(3) NOT NULL DEFAULT '',
  PRIMARY KEY (`TripNum`),
  KEY `TripType` (`TripType`),
  KEY `ArrivalLocCode` (`ArrivalLocCode`),
  KEY `DepartureLocCode` (`DepartureLocCode`),
  CONSTRAINT `trip_directory_ibfk_1` FOREIGN KEY (`TripType`) REFERENCES `tripcodes` (`TripType`),
  CONSTRAINT `trip_directory_ibfk_2` FOREIGN KEY (`ArrivalLocCode`) REFERENCES `locations` (`LocationCode`),
  CONSTRAINT `trip_directory_ibfk_3` FOREIGN KEY (`DepartureLocCode`) REFERENCES `locations` (`LocationCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_directory`
--

LOCK TABLES `trip_directory` WRITE;
/*!40000 ALTER TABLE `trip_directory` DISABLE KEYS */;
INSERT INTO `trip_directory` VALUES ('1027','P','2:00 PM','LAS','1:00 PM','BUF'),('3030','P','2:00 PM','NAS','1:00 PM','BOS'),('4567','T','7:30 AM','BUF','6:00 AM','ROC'),('546','B','11:30 PM','ROC','3:00 PM','JFK'),('6432','B','8:00 PM','ROC','1:00 PM','JFK'),('8794','T','11:00 PM','BUR','8:00 AM','LAS');
/*!40000 ALTER TABLE `trip_directory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_people`
--

DROP TABLE IF EXISTS `trip_people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_people` (
  `TripNum` varchar(10) NOT NULL DEFAULT '',
  `Date` date NOT NULL DEFAULT '0000-00-00',
  `PassengerID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`TripNum`,`Date`,`PassengerID`),
  KEY `Date` (`Date`),
  KEY `PassengerID` (`PassengerID`),
  CONSTRAINT `trip_people_ibfk_1` FOREIGN KEY (`TripNum`) REFERENCES `trip` (`TripNum`),
  CONSTRAINT `trip_people_ibfk_2` FOREIGN KEY (`Date`) REFERENCES `trip` (`Date`),
  CONSTRAINT `trip_people_ibfk_3` FOREIGN KEY (`PassengerID`) REFERENCES `passenger` (`PassengerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_people`
--

LOCK TABLES `trip_people` WRITE;
/*!40000 ALTER TABLE `trip_people` DISABLE KEYS */;
INSERT INTO `trip_people` VALUES ('3030','2015-08-14',9),('546','2015-09-07',5),('4567','2015-09-11',6),('4567','2015-09-11',7),('3030','2015-10-10',1),('3030','2015-10-10',2),('1027','2015-10-11',3),('1027','2015-10-11',4),('1027','2015-10-11',8),('6432','2015-11-04',10);
/*!40000 ALTER TABLE `trip_people` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tripcodes`
--

DROP TABLE IF EXISTS `tripcodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tripcodes` (
  `TripType` char(1) NOT NULL DEFAULT '',
  `TypeName` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`TripType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tripcodes`
--

LOCK TABLES `tripcodes` WRITE;
/*!40000 ALTER TABLE `tripcodes` DISABLE KEYS */;
INSERT INTO `tripcodes` VALUES ('B','Bus'),('P','Plane'),('T','Train');
/*!40000 ALTER TABLE `tripcodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zips`
--

DROP TABLE IF EXISTS `zips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zips` (
  `Zip` varchar(5) NOT NULL DEFAULT '',
  `City` varchar(100) NOT NULL DEFAULT '',
  `State` char(2) NOT NULL DEFAULT '',
  PRIMARY KEY (`Zip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zips`
--

LOCK TABLES `zips` WRITE;
/*!40000 ALTER TABLE `zips` DISABLE KEYS */;
INSERT INTO `zips` VALUES ('01701','Framingham','MA'),('13340','Frankfort','NY'),('14450','Fairport','NY'),('14551','Sodus','NY'),('14618','Rochester','NY'),('14623','Rochester','NY'),('14624','Rochester','NY'),('14626','Rochester','NY');
/*!40000 ALTER TABLE `zips` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-11-30  9:45:21
