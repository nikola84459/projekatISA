/*
 Navicat MariaDB Data Transfer

 Source Server         : projekat
 Source Server Type    : MariaDB
 Source Server Version : 100428 (10.4.28-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : projekat_placanje

 Target Server Type    : MariaDB
 Target Server Version : 100428 (10.4.28-MariaDB)
 File Encoding         : 65001

 Date: 16/01/2024 23:28:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for banka
-- ----------------------------
DROP TABLE IF EXISTS `banka`;
CREATE TABLE `banka`  (
  `banka_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password_hash` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `broj` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `link` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`banka_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of banka
-- ----------------------------
INSERT INTO `banka` VALUES (1, 'Erste banka', '12345', '584525', 'http://localhost:8082/placanje/transakcija');

-- ----------------------------
-- Table structure for transakcija
-- ----------------------------
DROP TABLE IF EXISTS `transakcija`;
CREATE TABLE `transakcija`  (
  `transakcija_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `broj_transakcije` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `broj_kartice` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `datum_i_vreme` datetime NOT NULL,
  `iznos` float NOT NULL,
  `banka_br_transakcije` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_uspesna` tinyint(1) NOT NULL,
  `banka_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`transakcija_id`) USING BTREE,
  INDEX `fk_transakcija_banka_id_banka_banka_id`(`banka_id`) USING BTREE,
  CONSTRAINT `fk_transakcija_banka_id_banka_banka_id` FOREIGN KEY (`banka_id`) REFERENCES `banka` (`banka_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transakcija
-- ----------------------------
INSERT INTO `transakcija` VALUES (1, '55456123074545', '64587446511238456879', '2023-10-11 17:43:37', 20000, '51234564658794645645678879', 1, 1);
INSERT INTO `transakcija` VALUES (2, '46eeb1e7-3812-41dc-8b31-427d699ebcf5', '5845252211225452', '2023-10-25 18:41:14', 20000, '24b7e7e7-ace8-4424-bad2-aab4310a00dd', 1, 1);
INSERT INTO `transakcija` VALUES (3, '9520c469-7529-414f-92be-d12c58c8eb14', '5845252211225452', '2023-10-26 19:06:52', 40000, 'd8f4689c-8261-4435-ade6-b38c6b31958b', 1, 1);
INSERT INTO `transakcija` VALUES (4, '61186490-2b4d-4b86-98d1-8a1f0b8ed74b', '5845252211225452', '2023-10-26 19:07:45', 20000, '3c36cf21-7cae-4988-8c09-978b93a18674', 1, 1);
INSERT INTO `transakcija` VALUES (5, '1732abb2-19d1-4d80-a7ca-96433acbe1ca', '5845252211225452', '2023-10-26 19:12:31', 40000, '759b8da0-9308-4ae5-a9fb-a201d2f2c7a9', 1, 1);
INSERT INTO `transakcija` VALUES (6, 'a9216c0a-d249-4097-9654-25aaf92925c4', '5845252211225452', '2023-10-26 19:14:44', 40000, '84852e4a-4454-4012-b0e8-92a1877860d2', 1, 1);
INSERT INTO `transakcija` VALUES (7, '6cff19ef-8497-4993-b961-9bcd1f234f56', '5845252211225452', '2023-10-26 19:23:13', 20000, '9c17dcfc-b972-440d-ac54-3d7527f29df6', 1, 1);
INSERT INTO `transakcija` VALUES (8, 'b834d058-db85-4f9d-ab58-1d9906db2230', '5845252211225452', '2023-10-26 19:23:36', 20000, '2f9eb030-1ad6-482c-9ab7-a497fcf116a5', 1, 1);
INSERT INTO `transakcija` VALUES (9, 'd4646742-f09a-4bf8-bc3d-0f1809efc3ed', '5845252211225452', '2023-10-26 19:26:48', 20000, '8aede9a1-64f1-4eba-8713-892e92d0060f', 1, 1);
INSERT INTO `transakcija` VALUES (10, 'b84c1a6f-1962-43b0-86ab-96455297cba6', '5845252211225452', '2023-10-26 19:30:01', 20000, '7eff662b-a6d9-4041-a538-15a0009e4288', 1, 1);
INSERT INTO `transakcija` VALUES (11, 'de14fd89-fc0e-4fea-97c4-69350e9c7d3e', '5845252211225452', '2023-10-26 19:32:11', 20000, 'bf94cbe9-872a-4ee2-b6c1-8d5524793fb1', 1, 1);
INSERT INTO `transakcija` VALUES (12, '7e2cd51a-3b49-49dd-8683-6c5eddc3d5fa', '5845252211225452', '2023-10-26 19:34:04', 20000, '86274afb-6433-4c12-a3d6-513285327792', 1, 1);
INSERT INTO `transakcija` VALUES (13, 'ee433f6d-aae6-4f9f-8ab6-dd8e1782770b', '5845252211225452', '2023-10-26 19:40:54', 20000, '544c8d05-1001-4b67-b9e3-b69bf3173f25', 1, 1);
INSERT INTO `transakcija` VALUES (14, '1471cb7f-0f66-4ae5-b15a-388662ef1878', '5845252211225452', '2023-10-26 19:42:21', 20000, 'e7f2b800-eb9b-4902-a7fb-e89c7a933a74', 1, 1);
INSERT INTO `transakcija` VALUES (15, '7913e52c-9408-45a7-92d8-57485e515e39', '5845252211225452', '2023-10-26 19:43:35', 20000, '7d464d90-463f-4311-aa1a-6a38fee671cf', 1, 1);
INSERT INTO `transakcija` VALUES (16, '116f3ea0-425a-480d-abff-35ef9d68542b', '5845252211225452', '2023-10-26 19:44:12', 20000, '7fee63e6-c967-4212-8451-b4cded5bb8f8', 1, 1);
INSERT INTO `transakcija` VALUES (17, '9fa7b840-ded1-46b5-b5f7-544264caaf87', '5845252211225452', '2023-10-26 19:46:01', 20000, '382e2dbc-01a2-4b7c-a8dc-7fc927cfbdd4', 1, 1);
INSERT INTO `transakcija` VALUES (18, '467ac62b-2aef-48f3-9356-9f7398f603c4', '5845252211225452', '2023-10-26 19:48:59', 20000, 'f669bfb0-f3ff-40bd-9d17-20c7a9436642', 1, 1);
INSERT INTO `transakcija` VALUES (19, '3c91af65-a19d-466f-84d6-c9b21a9a5301', '5845252211225452', '2023-10-26 19:50:01', 20000, 'bcce218e-cc2e-4e31-89fc-43fbfd408cdc', 1, 1);
INSERT INTO `transakcija` VALUES (20, '45bdc1e6-67bb-4089-a89b-f138db416296', '5845252211225452', '2023-10-26 19:51:44', 20000, 'd353a4b9-e292-4fd2-a615-911802e0caf5', 1, 1);
INSERT INTO `transakcija` VALUES (21, '689fd508-f7bc-41a2-9dd0-37a8ac091ae8', '5845252211225452', '2023-10-26 19:54:12', 20000, '66eaf087-6414-45e5-90db-d0474b9de3d6', 1, 1);
INSERT INTO `transakcija` VALUES (22, '9bdca7d4-c9a0-4899-ac51-9269b9de8d82', '5845252211225452', '2023-10-26 20:00:42', 20000, 'b8082880-4aa7-4318-9fbe-79bd6a463531', 1, 1);
INSERT INTO `transakcija` VALUES (23, '7a5dc342-141e-4133-8b18-a4c53ef37bef', '5845252211225452', '2023-10-26 20:03:26', 20000, '08ce98b1-8b5f-4aa4-8cad-58ac45d03775', 1, 1);
INSERT INTO `transakcija` VALUES (24, '1bb75b3e-e72a-4c1e-9977-108105966244', '5845252211225452', '2023-10-26 20:06:10', 40000, '4242d8a1-631a-457c-9cc2-85b89129b110', 1, 1);
INSERT INTO `transakcija` VALUES (25, 'bf760f55-8551-4165-8d92-b97d604dce2f', '5845252211225452', '2023-10-26 20:09:37', 40000, '1ebf3456-6109-4775-907b-43b72002a5a9', 1, 1);
INSERT INTO `transakcija` VALUES (26, '325d7bdd-591b-42e6-91eb-c12a06c78885', '5845252211225452', '2023-10-26 20:11:48', 40000, '103c986b-c995-4aba-bafd-3986616f65a4', 1, 1);
INSERT INTO `transakcija` VALUES (27, '40a8d845-aada-4627-89e2-6edfcd90ec68', '5845252211225452', '2023-10-26 20:15:09', 40000, '77f28725-68ee-46e5-97cf-e87f9ff9ecae', 1, 1);
INSERT INTO `transakcija` VALUES (28, 'b48eef38-ef70-4b63-b62b-4d752fd07edc', '5845252211225452', '2023-10-26 20:17:00', 40000, '4075881d-a838-46e2-9f9f-f60464553ab9', 1, 1);
INSERT INTO `transakcija` VALUES (29, '05c0ed30-2ed2-42e2-a9db-8a31b85b9abd', '5845252211225452', '2023-10-26 20:21:08', 40000, 'ffa084d3-e9e6-4441-bf78-c6991c540050', 1, 1);
INSERT INTO `transakcija` VALUES (30, '2a3f87dc-7bc3-4179-a0b9-630fd9570f76', '5845252211225452', '2023-10-26 20:23:13', 40000, '5ffc50fc-8dbb-4440-b026-95da3bca56cd', 1, 1);
INSERT INTO `transakcija` VALUES (31, 'e2a324b8-4c58-403d-93f0-e51fbc2168a8', '5845252211225452', '2023-10-26 20:24:27', 40000, '03dc7abb-e161-40bf-a067-58e0eeeba7a5', 1, 1);
INSERT INTO `transakcija` VALUES (32, '41c74396-06c8-444e-ae7d-4de81ed8e23b', '5845252211225452', '2023-10-26 20:25:50', 40000, '8fe28aa1-7a7e-4b47-adf0-dac2efc4dee2', 1, 1);
INSERT INTO `transakcija` VALUES (33, '0667154f-7d21-4507-b834-ba4adc63aa1a', '5845252211225452', '2023-10-28 14:50:22', 500, '6164ad4c-d0d5-40b8-9735-b86d255f00bd', 1, 1);
INSERT INTO `transakcija` VALUES (34, 'a36028ac-c346-4d9b-9e75-dc8f101426c0', '5845252211225452', '2023-10-28 14:59:51', 2000, '4651bfdd-66b4-4c63-9717-c4d104d3d30b', 1, 1);
INSERT INTO `transakcija` VALUES (35, '01b1db59-8f36-4585-a278-54878a8abcdf', '5845252211225452', '2023-10-28 15:03:07', 2000, '4c1445fd-610e-4d1d-990d-7947412b82a1', 1, 1);
INSERT INTO `transakcija` VALUES (36, 'c54f7fc3-8cdd-42f9-9384-c764e7d27616', '5845252211225452', '2023-10-28 15:14:01', 20000, '9bb78b26-92f2-4fee-a4ff-41e9c8e20420', 1, 1);
INSERT INTO `transakcija` VALUES (37, '45613b7f-6fea-475d-969e-e297ab32d7c7', '5845252211225452', '2023-10-28 15:15:42', 20000, '6b824489-9cce-4b61-9450-c0540484c741', 1, 1);
INSERT INTO `transakcija` VALUES (38, '390b678c-5e40-438b-bd07-0197e0870212', '5845252211225452', '2023-10-28 15:16:47', 20000, '3c58a964-2b42-4e22-91fa-6286142b7dd4', 1, 1);
INSERT INTO `transakcija` VALUES (39, '39e04661-0e27-4f93-879a-ed2eb9356a1e', '5845252211225452', '2023-10-28 15:26:52', 20000, '126ce224-eba2-4517-8388-f45c43c733f7', 1, 1);
INSERT INTO `transakcija` VALUES (40, '9d8049f6-8988-4a87-a92c-ed48f81b855a', '5845252211225452', '2023-11-26 21:11:23', 20000, 'd7a7aec4-bff1-4b35-b46f-4739a5262866', 1, 1);
INSERT INTO `transakcija` VALUES (41, '85bf3b2e-ab01-4b72-a921-1b932968474f', '5845252211225452', '2023-11-26 21:12:37', 500, '3c352a38-3bae-4dd0-b875-7b6ce8fed6c9', 1, 1);
INSERT INTO `transakcija` VALUES (42, '3874f993-e371-45a5-a2e5-63f5e5f3cdec', '5845252211225452', '2024-01-11 16:33:24', 20000, 'de483927-f7a6-43a8-b1a1-b22ef0c052f1', 1, 1);
INSERT INTO `transakcija` VALUES (43, 'c7dea54a-7f6c-4007-a5b0-4dcbb0b862ff', '5845252211225452', '2024-01-11 17:16:30', 1000, '458a096f-5bbc-4ce3-800c-c7fb96d8931d', 1, 1);
INSERT INTO `transakcija` VALUES (44, '9ef1953f-0fdd-4073-864c-3f1844ca012a', '5845252211225452', '2024-01-13 17:24:07', 1500, '6217b411-3d53-4cff-aaeb-dd4d74467943', 1, 1);
INSERT INTO `transakcija` VALUES (45, '50671452-e970-46fb-a4e0-968c362f46b1', '5845252211225452', '2024-01-14 19:22:08', 40000, 'eb7ac162-a013-4e85-a8dc-87a3ecf6fcaf', 1, 1);
INSERT INTO `transakcija` VALUES (46, '0fac56dd-f908-4e41-93ad-c0a2ad486ee2', '5845252211225452', '2024-01-14 19:30:35', 20000, 'da6d9508-2823-465a-8f7a-a205cd94638e', 1, 1);
INSERT INTO `transakcija` VALUES (47, 'f8e9ad28-e9d7-4a54-bce3-f88040d20090', '5845252211225452', '2024-01-14 19:32:15', 20000, 'cbf8c163-7b9c-46f0-8eeb-3facdb68f03b', 1, 1);
INSERT INTO `transakcija` VALUES (48, 'e853b62b-068b-4e5e-9e8b-055c4e2ddfd0', '5845252211225452', '2024-01-14 19:34:42', 20000, '0e58e5cb-99b2-4134-b538-d9300b1d3fe8', 1, 1);
INSERT INTO `transakcija` VALUES (49, 'a3d26986-7985-4faa-93a7-276ab40291f9', '5845252211225452', '2024-01-14 19:35:11', 40000, 'dbb77bab-2cab-4769-8a2b-9ef54a3d3097', 1, 1);
INSERT INTO `transakcija` VALUES (50, '6efe9c85-274d-4bcb-9af3-cba90126ece0', '5845252211225452', '2024-01-14 19:35:50', 20000, '9815378b-41d3-41ce-8e0d-693dae50ce57', 1, 1);
INSERT INTO `transakcija` VALUES (51, 'eceb4ac1-e63e-4849-8b0e-8084f5ce0c96', '5845252211225452', '2024-01-14 19:49:54', 40000, 'ec602244-a606-485d-a045-ce179075c6a2', 1, 1);
INSERT INTO `transakcija` VALUES (52, '18e229b0-1452-4994-8062-319416da8b55', '5845252211225452', '2024-01-14 19:55:31', 20000, '4e5dd591-8a0b-4f93-9aa9-e0d8e9443aea', 1, 1);
INSERT INTO `transakcija` VALUES (53, 'b70d3b3d-049f-4aa2-bdf8-697ebb9ca460', '5845252211225452', '2024-01-14 19:56:22', 40000, 'eae577da-4ba4-4e42-9ab2-6cf5ccc46d77', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
