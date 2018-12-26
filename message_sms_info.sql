/*
 Navicat Premium Data Transfer

 Source Server         : DB_M1
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : 192.168.0.130:3306
 Source Schema         : messagesql

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 26/12/2018 22:06:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message_sms_info
-- ----------------------------
DROP TABLE IF EXISTS `message_sms_info`;
CREATE TABLE `message_sms_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creator` int(11) NOT NULL COMMENT '创建者',
  `creater_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updater` int(11) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `serial_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送流水号',
  `phone_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `send_status` tinyint(4) NULL DEFAULT 1 COMMENT '发送状态 1：等待回执，2：发送失败，3：发送成功',
  `err_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运营商短信错误码',
  `template_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板ID',
  `content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信内容',
  `send_date` timestamp(0) NULL DEFAULT NULL COMMENT '发送时间',
  `receive_date` timestamp(0) NULL DEFAULT NULL COMMENT '接收时间',
  `biz_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阿里回调id',
  `is_del` tinyint(4) NULL DEFAULT 0 COMMENT '删除标识 0：正常，1：已删除',
  `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
