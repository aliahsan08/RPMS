CREATE DATABASE  IF NOT EXISTS `rpms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `rpms`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rpms
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrators`
--

DROP TABLE IF EXISTS `administrators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrators` (
  `user_id` varchar(36) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(10) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL,
  `official_email` varchar(100) NOT NULL,
  `supervisor_name` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrators`
--

LOCK TABLES `administrators` WRITE;
/*!40000 ALTER TABLE `administrators` DISABLE KEYS */;
INSERT INTO `administrators` VALUES ('A001','superadmin','admin123','Riaz Malik',45,'Other','03001245125','admin@example.com','System Admin','ranaaliahsan78600@gmail.com','Ahmed Ali');
/*!40000 ALTER TABLE `administrators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `appointment_id` varchar(36) NOT NULL,
  `patient_id` varchar(36) NOT NULL,
  `doctor_id` varchar(36) NOT NULL,
  `appointment_datetime` datetime NOT NULL,
  `purpose` text NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`appointment_id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `status_check` CHECK ((`status` in (_utf8mb4'REQUESTED',_utf8mb4'CONFIRMED',_utf8mb4'CANCELLED',_utf8mb4'COMPLETED')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES ('AP001','P001','D001','2025-05-08 10:00:00','Regular check-up','REQUESTED'),('AP002','P002','D002','2025-05-09 14:00:00','Headache and dizziness','CONFIRMED'),('AP003','P002','D001','2025-12-12 12:15:00','Regular Checkup','REQUESTED'),('AP004','P002','D001','2024-12-12 12:20:00','xyz','REQUESTED'),('AP005','P002','D002','2025-12-12 12:15:00','xtz','CONFIRMED'),('AP006','P002','D002','2025-08-12 12:30:00','yes','REQUESTED');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_feedback`
--

DROP TABLE IF EXISTS `doctor_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_feedback` (
  `feedback_id` varchar(36) NOT NULL,
  `doctor_id` varchar(36) NOT NULL,
  `patient_id` varchar(36) NOT NULL,
  `timestamp` datetime NOT NULL,
  `diagnosis` text NOT NULL,
  `recommendations` text NOT NULL,
  `prescription` text NOT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `doctor_id` (`doctor_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `doctor_feedback_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `doctor_feedback_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_feedback`
--

LOCK TABLES `doctor_feedback` WRITE;
/*!40000 ALTER TABLE `doctor_feedback` DISABLE KEYS */;
INSERT INTO `doctor_feedback` VALUES ('FB001','D001','P001','2025-05-06 11:00:00','All vitals normal','Continue current lifestyle','Vitamin D supplement'),('FB002','D002','P002','2025-05-06 12:00:00','Possible viral infection','Rest, hydration','Paracetamol 500mg'),('FB003','D002','P003','2025-05-06 12:00:00','Possible viral infection','Rest, hydration','Paracetamol 500mg'),('FB004','D002','P002','2025-05-14 02:33:57','Headache','ABC','XYZ'),('FB005','D002','P002','2025-05-14 03:45:30','XYZ','XYZ','XYZ'),('FB006','D002','P002','2025-05-14 11:34:10','xyz','xyz','xyz'),('FB007','D002','P002','2025-05-14 11:43:19','avc','abc','acx');
/*!40000 ALTER TABLE `doctor_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_patient_assignments`
--

DROP TABLE IF EXISTS `doctor_patient_assignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_patient_assignments` (
  `doctor_id` varchar(36) NOT NULL,
  `patient_id` varchar(36) NOT NULL,
  PRIMARY KEY (`doctor_id`,`patient_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `doctor_patient_assignments_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `doctor_patient_assignments_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_patient_assignments`
--

LOCK TABLES `doctor_patient_assignments` WRITE;
/*!40000 ALTER TABLE `doctor_patient_assignments` DISABLE KEYS */;
INSERT INTO `doctor_patient_assignments` VALUES ('D002','P001'),('D001','P002'),('D002','P002');
/*!40000 ALTER TABLE `doctor_patient_assignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `user_id` varchar(36) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(10) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `specialization` varchar(100) NOT NULL,
  `working_hours` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES ('D001','dr_saarim','doctor123','Saarim Ejaz',31,'Male','03001231233','ranaaliahsan78600@gmail.com','Surgeon','9am-5pm'),('D002','dr_fatima','doctor123','Fatima Zahid',38,'Female','03001234242','ranaaliahsan78600@gmail.com','Neurologist','10am-6pm');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `user_id` varchar(36) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(10) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `assigned_doctor` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `assigned_doctor` (`assigned_doctor`),
  CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`assigned_doctor`) REFERENCES `doctors` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES ('P001','a_ahsan','patient123','Ali Ahsan',25,'Male','030501204','ranaaliahsan78600@gmail.com','D001'),('P002','d_fatima','patient123','Dua Fatima',25,'Female','03001245125','ranaaliahsan78600@gmail.com','D002'),('P003','m_dawood','patient123','M Dawood',25,'Female','03001245125','ranaaliahsan78600@gmail.com','D002');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vital_records`
--

DROP TABLE IF EXISTS `vital_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vital_records` (
  `record_id` varchar(36) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `timestamp` datetime NOT NULL,
  `temperature` double NOT NULL,
  `heart_rate` int NOT NULL,
  `systolic_bp` int NOT NULL,
  `diastolic_bp` int NOT NULL,
  `respiration_rate` int NOT NULL,
  `oxygen_saturation` double NOT NULL,
  `notes` text,
  `alerts` text,
  `is_critical` tinyint(1) NOT NULL,
  PRIMARY KEY (`record_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `vital_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `patients` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vital_records`
--

LOCK TABLES `vital_records` WRITE;
/*!40000 ALTER TABLE `vital_records` DISABLE KEYS */;
INSERT INTO `vital_records` VALUES ('VR001','P002','2025-05-10 19:49:11',37,60,100,70,15,80,'','[Abnormal Temperature: 123.0°C, Abnormal Heart Rate: 214 bpm, Abnormal Diastolic BP: 521 mmHg, Abnormal Respiration Rate: 31 breaths/min]',1),('VR002','P002','2025-05-10 21:49:11',37.5,80,110,80,20,100,'','[Abnormal Temperature: 123.0°C, Abnormal Heart Rate: 214 bpm, Abnormal Diastolic BP: 521 mmHg, Abnormal Respiration Rate: 31 breaths/min]',1),('VR003','P002','2025-05-12 16:43:43',37,80,80,80,17,100,'','[Abnormal Systolic BP: 80 mmHg]',1),('VR004','P002','2025-05-13 12:52:35',37,80,80,80,18,80,'','[Abnormal Systolic BP: 80 mmHg, Low Oxygen Saturation: 80.0%]',1),('VR005','P002','2025-05-13 12:53:51',37,80,80,80,18,80,'','[Abnormal Systolic BP: 80 mmHg, Low Oxygen Saturation: 80.0%]',1),('VR006','P001','2025-05-10 21:49:11',37.5,80,110,80,20,100,'','[Abnormal Temperature: 123.0°C, Abnormal Heart Rate: 214 bpm, Abnormal Diastolic BP: 521 mmHg, Abnormal Respiration Rate: 31 breaths/min]',1),('VR007','P003','2025-05-12 16:43:43',37,80,80,80,17,100,'','[Abnormal Systolic BP: 80 mmHg]',1),('VR008','P002','2025-05-14 02:27:27',37,65,60,70,70,20,'','[Abnormal Systolic BP: 60 mmHg, Abnormal Respiration Rate: 70 breaths/min, Low Oxygen Saturation: 20.0%]',1),('VR009','P002','2025-05-14 03:47:04',37,80,80,70,70,20,'','[Abnormal Systolic BP: 80 mmHg, Abnormal Respiration Rate: 70 breaths/min, Low Oxygen Saturation: 20.0%]',1),('VR010','P001','2025-05-14 10:56:54',37,80,80,80,20,85,'','[Abnormal Systolic BP: 80 mmHg, Low Oxygen Saturation: 85.0%]',1),('VR011','P003','2025-05-14 11:15:10',37,80,80,80,20,85,'','[Abnormal Systolic BP: 80 mmHg, Low Oxygen Saturation: 85.0%]',1),('VR012','P002','2025-05-14 11:35:39',37,80,70,80,110,20,'','[Abnormal Systolic BP: 70 mmHg, Abnormal Respiration Rate: 110 breaths/min, Low Oxygen Saturation: 20.0%]',1),('VR013','P002','2025-05-14 11:36:18',37,80,80,80,20,85,'','[Abnormal Systolic BP: 80 mmHg, Low Oxygen Saturation: 85.0%]',1),('VR014','P002','2025-05-14 11:40:06',37,80,80,80,20,85,'','[Abnormal Systolic BP: 80 mmHg, Low Oxygen Saturation: 85.0%]',1);
/*!40000 ALTER TABLE `vital_records` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-18 22:28:41
