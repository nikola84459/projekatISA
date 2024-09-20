/*
 Navicat MariaDB Data Transfer

 Source Server         : projekat
 Source Server Type    : MariaDB
 Source Server Version : 100428 (10.4.28-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : projekat_banka

 Target Server Type    : MariaDB
 Target Server Version : 100428 (10.4.28-MariaDB)
 File Encoding         : 65001

 Date: 16/01/2024 23:28:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kartica
-- ----------------------------
DROP TABLE IF EXISTS `kartica`;
CREATE TABLE `kartica`  (
  `kartica_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `broj_kartice` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `izdavac` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `datum_isteka` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `cvv` varchar(4) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `racun_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`kartica_id`) USING BTREE,
  INDEX `fk_kartica_racun_id_racun_racun_id`(`racun_id`) USING BTREE,
  CONSTRAINT `fk_kartica_racun_id_racun_racun_id` FOREIGN KEY (`racun_id`) REFERENCES `racun` (`racun_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kartica
-- ----------------------------
INSERT INTO `kartica` VALUES (1, '5845252211225452', 'Visa', '02/28', '544', 1);
INSERT INTO `kartica` VALUES (2, '52001201200220124', 'Master Card', '01/24', '201', 2);

-- ----------------------------
-- Table structure for korisnik
-- ----------------------------
DROP TABLE IF EXISTS `korisnik`;
CREATE TABLE `korisnik`  (
  `korisnik_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ime` varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`korisnik_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of korisnik
-- ----------------------------
INSERT INTO `korisnik` VALUES (1, 'Marko', 'Markovic', 'mmarkovic@singidunum.ac.rs');
INSERT INTO `korisnik` VALUES (2, 'Pera', 'Perić', 'pperic@singidunum.ac.rs');

-- ----------------------------
-- Table structure for korisnik_pravno_lice
-- ----------------------------
DROP TABLE IF EXISTS `korisnik_pravno_lice`;
CREATE TABLE `korisnik_pravno_lice`  (
  `korisnik_pravno_lice_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `naziv` varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pib` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `broj` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`korisnik_pravno_lice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of korisnik_pravno_lice
-- ----------------------------
INSERT INTO `korisnik_pravno_lice` VALUES (1, 'Univerzitet Singidunum', '54585215478', '02545441254');

-- ----------------------------
-- Table structure for korisnik_pravno_lice_racun
-- ----------------------------
DROP TABLE IF EXISTS `korisnik_pravno_lice_racun`;
CREATE TABLE `korisnik_pravno_lice_racun`  (
  `korisnik_pravno_lice_racun_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `korisnik_pravno_lice_id` int(10) UNSIGNED NOT NULL,
  `racun_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`korisnik_pravno_lice_racun_id`) USING BTREE,
  INDEX `fk_korisnik_pravno_lice_id_korisnik_pravno_lice_id`(`korisnik_pravno_lice_id`) USING BTREE,
  INDEX `fk_korisnik_pravno_lice_racun_id_racun_racun_id`(`racun_id`) USING BTREE,
  CONSTRAINT `fk_korisnik_pravno_lice_id_korisnik_pravno_lice_id` FOREIGN KEY (`korisnik_pravno_lice_id`) REFERENCES `korisnik_pravno_lice` (`korisnik_pravno_lice_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_korisnik_pravno_lice_racun_id_racun_racun_id` FOREIGN KEY (`racun_id`) REFERENCES `racun` (`racun_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of korisnik_pravno_lice_racun
-- ----------------------------
INSERT INTO `korisnik_pravno_lice_racun` VALUES (1, 1, 3);

-- ----------------------------
-- Table structure for korisnik_racun
-- ----------------------------
DROP TABLE IF EXISTS `korisnik_racun`;
CREATE TABLE `korisnik_racun`  (
  `korisnik_racun_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `korisnik_id` int(10) UNSIGNED NOT NULL,
  `racun_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`korisnik_racun_id`) USING BTREE,
  INDEX `fk_korisnik_racun_korisnik_id_korisnik_korisnik_id`(`korisnik_id`) USING BTREE,
  INDEX `FKr5cdu5p9pvfsbq4q8dpk49hgd`(`racun_id`) USING BTREE,
  CONSTRAINT `FKr5cdu5p9pvfsbq4q8dpk49hgd` FOREIGN KEY (`racun_id`) REFERENCES `racun` (`racun_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_korisnik_racun_korisnik_id_korisnik_korisnik_id` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of korisnik_racun
-- ----------------------------
INSERT INTO `korisnik_racun` VALUES (1, 1, 1);
INSERT INTO `korisnik_racun` VALUES (2, 2, 2);

-- ----------------------------
-- Table structure for korisnik_racun_model
-- ----------------------------
DROP TABLE IF EXISTS `korisnik_racun_model`;
CREATE TABLE `korisnik_racun_model`  (
  `korisnik_racun_model_id` int(11) NOT NULL AUTO_INCREMENT,
  `korisnik_id` int(11) NULL DEFAULT NULL,
  `racun_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`korisnik_racun_model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of korisnik_racun_model
-- ----------------------------

-- ----------------------------
-- Table structure for racun
-- ----------------------------
DROP TABLE IF EXISTS `racun`;
CREATE TABLE `racun`  (
  `racun_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `br_racuna` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `stanje` float NOT NULL,
  `datumOtvaranja` date NOT NULL,
  `datum_otvaranja` date NULL DEFAULT NULL,
  PRIMARY KEY (`racun_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of racun
-- ----------------------------
INSERT INTO `racun` VALUES (1, '2545252145874589788', 149374000, '2023-08-14', NULL);
INSERT INTO `racun` VALUES (2, '21564897484521456489', 125000, '2023-08-28', NULL);
INSERT INTO `racun` VALUES (3, '52521451565485121548', 1803000, '2023-06-04', NULL);

-- ----------------------------
-- Table structure for transakcija
-- ----------------------------
DROP TABLE IF EXISTS `transakcija`;
CREATE TABLE `transakcija`  (
  `transakcija_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `broj` varchar(150) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `datum_i_vreme` datetime NOT NULL,
  `iznos` float NOT NULL,
  `kartica_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`transakcija_id`) USING BTREE,
  INDEX `fk_transakcija_kartica_id_kartica_kartica_id`(`kartica_id`) USING BTREE,
  CONSTRAINT `fk_transakcija_kartica_id_kartica_kartica_id` FOREIGN KEY (`kartica_id`) REFERENCES `kartica` (`kartica_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transakcija
-- ----------------------------
INSERT INTO `transakcija` VALUES (1, '4512315647896556123', '2023-10-03 17:35:11', 20000, 1);
INSERT INTO `transakcija` VALUES (2, '24b7e7e7-ace8-4424-bad2-aab4310a00dd', '2023-10-25 18:41:14', 20000, 1);
INSERT INTO `transakcija` VALUES (3, 'd8f4689c-8261-4435-ade6-b38c6b31958b', '2023-10-26 19:06:52', 40000, 1);
INSERT INTO `transakcija` VALUES (4, '3c36cf21-7cae-4988-8c09-978b93a18674', '2023-10-26 19:07:45', 20000, 1);
INSERT INTO `transakcija` VALUES (5, '759b8da0-9308-4ae5-a9fb-a201d2f2c7a9', '2023-10-26 19:12:31', 40000, 1);
INSERT INTO `transakcija` VALUES (6, '84852e4a-4454-4012-b0e8-92a1877860d2', '2023-10-26 19:14:44', 40000, 1);
INSERT INTO `transakcija` VALUES (7, '9c17dcfc-b972-440d-ac54-3d7527f29df6', '2023-10-26 19:23:13', 20000, 1);
INSERT INTO `transakcija` VALUES (8, '2f9eb030-1ad6-482c-9ab7-a497fcf116a5', '2023-10-26 19:23:36', 20000, 1);
INSERT INTO `transakcija` VALUES (9, '8aede9a1-64f1-4eba-8713-892e92d0060f', '2023-10-26 19:26:48', 20000, 1);
INSERT INTO `transakcija` VALUES (10, '7eff662b-a6d9-4041-a538-15a0009e4288', '2023-10-26 19:30:01', 20000, 1);
INSERT INTO `transakcija` VALUES (11, 'bf94cbe9-872a-4ee2-b6c1-8d5524793fb1', '2023-10-26 19:32:11', 20000, 1);
INSERT INTO `transakcija` VALUES (12, '86274afb-6433-4c12-a3d6-513285327792', '2023-10-26 19:34:04', 20000, 1);
INSERT INTO `transakcija` VALUES (13, '544c8d05-1001-4b67-b9e3-b69bf3173f25', '2023-10-26 19:40:54', 20000, 1);
INSERT INTO `transakcija` VALUES (14, 'e7f2b800-eb9b-4902-a7fb-e89c7a933a74', '2023-10-26 19:42:21', 20000, 1);
INSERT INTO `transakcija` VALUES (15, '7d464d90-463f-4311-aa1a-6a38fee671cf', '2023-10-26 19:43:35', 20000, 1);
INSERT INTO `transakcija` VALUES (16, '7fee63e6-c967-4212-8451-b4cded5bb8f8', '2023-10-26 19:44:12', 20000, 1);
INSERT INTO `transakcija` VALUES (17, '382e2dbc-01a2-4b7c-a8dc-7fc927cfbdd4', '2023-10-26 19:46:01', 20000, 1);
INSERT INTO `transakcija` VALUES (18, 'f669bfb0-f3ff-40bd-9d17-20c7a9436642', '2023-10-26 19:48:59', 20000, 1);
INSERT INTO `transakcija` VALUES (19, 'bcce218e-cc2e-4e31-89fc-43fbfd408cdc', '2023-10-26 19:50:01', 20000, 1);
INSERT INTO `transakcija` VALUES (20, 'd353a4b9-e292-4fd2-a615-911802e0caf5', '2023-10-26 19:51:44', 20000, 1);
INSERT INTO `transakcija` VALUES (21, '66eaf087-6414-45e5-90db-d0474b9de3d6', '2023-10-26 19:54:12', 20000, 1);
INSERT INTO `transakcija` VALUES (22, 'b8082880-4aa7-4318-9fbe-79bd6a463531', '2023-10-26 20:00:42', 20000, 1);
INSERT INTO `transakcija` VALUES (23, '08ce98b1-8b5f-4aa4-8cad-58ac45d03775', '2023-10-26 20:03:26', 20000, 1);
INSERT INTO `transakcija` VALUES (24, '4242d8a1-631a-457c-9cc2-85b89129b110', '2023-10-26 20:06:10', 40000, 1);
INSERT INTO `transakcija` VALUES (25, '1ebf3456-6109-4775-907b-43b72002a5a9', '2023-10-26 20:09:37', 40000, 1);
INSERT INTO `transakcija` VALUES (26, '103c986b-c995-4aba-bafd-3986616f65a4', '2023-10-26 20:11:48', 40000, 1);
INSERT INTO `transakcija` VALUES (27, '77f28725-68ee-46e5-97cf-e87f9ff9ecae', '2023-10-26 20:15:09', 40000, 1);
INSERT INTO `transakcija` VALUES (28, '4075881d-a838-46e2-9f9f-f60464553ab9', '2023-10-26 20:17:00', 40000, 1);
INSERT INTO `transakcija` VALUES (29, 'ffa084d3-e9e6-4441-bf78-c6991c540050', '2023-10-26 20:21:08', 40000, 1);
INSERT INTO `transakcija` VALUES (30, '5ffc50fc-8dbb-4440-b026-95da3bca56cd', '2023-10-26 20:23:13', 40000, 1);
INSERT INTO `transakcija` VALUES (31, '03dc7abb-e161-40bf-a067-58e0eeeba7a5', '2023-10-26 20:24:27', 40000, 1);
INSERT INTO `transakcija` VALUES (32, '8fe28aa1-7a7e-4b47-adf0-dac2efc4dee2', '2023-10-26 20:25:50', 40000, 1);
INSERT INTO `transakcija` VALUES (33, '6164ad4c-d0d5-40b8-9735-b86d255f00bd', '2023-10-28 14:50:22', 500, 1);
INSERT INTO `transakcija` VALUES (34, '4651bfdd-66b4-4c63-9717-c4d104d3d30b', '2023-10-28 14:59:51', 2000, 1);
INSERT INTO `transakcija` VALUES (35, '4c1445fd-610e-4d1d-990d-7947412b82a1', '2023-10-28 15:03:07', 2000, 1);
INSERT INTO `transakcija` VALUES (36, '9bb78b26-92f2-4fee-a4ff-41e9c8e20420', '2023-10-28 15:14:01', 20000, 1);
INSERT INTO `transakcija` VALUES (37, '6b824489-9cce-4b61-9450-c0540484c741', '2023-10-28 15:15:42', 20000, 1);
INSERT INTO `transakcija` VALUES (38, '3c58a964-2b42-4e22-91fa-6286142b7dd4', '2023-10-28 15:16:47', 20000, 1);
INSERT INTO `transakcija` VALUES (39, '126ce224-eba2-4517-8388-f45c43c733f7', '2023-10-28 15:26:52', 20000, 1);
INSERT INTO `transakcija` VALUES (40, 'd7a7aec4-bff1-4b35-b46f-4739a5262866', '2023-11-26 21:11:23', 20000, 1);
INSERT INTO `transakcija` VALUES (41, '3c352a38-3bae-4dd0-b875-7b6ce8fed6c9', '2023-11-26 21:12:37', 500, 1);
INSERT INTO `transakcija` VALUES (42, 'de483927-f7a6-43a8-b1a1-b22ef0c052f1', '2024-01-11 16:33:23', 20000, 1);
INSERT INTO `transakcija` VALUES (43, '458a096f-5bbc-4ce3-800c-c7fb96d8931d', '2024-01-11 17:16:30', 1000, 1);
INSERT INTO `transakcija` VALUES (44, '6217b411-3d53-4cff-aaeb-dd4d74467943', '2024-01-13 17:24:07', 1500, 1);
INSERT INTO `transakcija` VALUES (45, 'eb7ac162-a013-4e85-a8dc-87a3ecf6fcaf', '2024-01-14 19:22:08', 40000, 1);
INSERT INTO `transakcija` VALUES (46, 'da6d9508-2823-465a-8f7a-a205cd94638e', '2024-01-14 19:30:35', 20000, 1);
INSERT INTO `transakcija` VALUES (47, 'cbf8c163-7b9c-46f0-8eeb-3facdb68f03b', '2024-01-14 19:32:15', 20000, 1);
INSERT INTO `transakcija` VALUES (48, '0e58e5cb-99b2-4134-b538-d9300b1d3fe8', '2024-01-14 19:34:42', 20000, 1);
INSERT INTO `transakcija` VALUES (49, 'dbb77bab-2cab-4769-8a2b-9ef54a3d3097', '2024-01-14 19:35:11', 40000, 1);
INSERT INTO `transakcija` VALUES (50, '9815378b-41d3-41ce-8e0d-693dae50ce57', '2024-01-14 19:35:50', 20000, 1);
INSERT INTO `transakcija` VALUES (51, 'ec602244-a606-485d-a045-ce179075c6a2', '2024-01-14 19:49:54', 40000, 1);
INSERT INTO `transakcija` VALUES (52, '4e5dd591-8a0b-4f93-9aa9-e0d8e9443aea', '2024-01-14 19:55:31', 20000, 1);
INSERT INTO `transakcija` VALUES (53, 'eae577da-4ba4-4e42-9ab2-6cf5ccc46d77', '2024-01-14 19:56:22', 40000, 1);

SET FOREIGN_KEY_CHECKS = 1;
