/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.102.17
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : 192.168.102.17
 Source Database       : eie

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : utf-8

 Date: 09/10/2018 14:06:00 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(200) DEFAULT NULL COMMENT '菜单地址',
  `parent_menu_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `sort_number` int(11) DEFAULT NULL COMMENT '排序号',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态',
  `menu_type` tinyint(4) DEFAULT '0' COMMENT '菜单类型（1-根节点；2-子结点；3-按钮）',
  `button_id` varchar(50) DEFAULT NULL COMMENT '按钮的ID名称',
  `icon_name` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `target` varchar(50) DEFAULT NULL COMMENT '菜单指向',
  `extra_url` varchar(500) DEFAULT NULL COMMENT '额外参数',
  `create_time` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(11) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '系统管理', null, '0', '2', '1', '1', null, null, null, null, '0', '0', '0'), ('2', '用户管理', '/SysUserController/sysUserList', '1', '1', '1', '2', '', '', null, 'queryAllSysUser', '0', '1473785365', '0'), ('3', '角色管理', '/SysRoleController/sysRoleList', '1', '2', '1', '2', '', '', null, 'queryAllSysRole', '0', '0', '0'), ('4', '菜单管理', '/SysMenuController/sysMenuList', '1', '3', '1', '2', null, null, null, null, '0', '0', '0'), ('5', '查询角色列表', '', '3', '1', '1', '3', '', '', null, 'queryAllSysRole', '0', '1473870231', '0'), ('6', '新增角色', '', '3', '2', '1', '3', 'sysRoleAddBtn', '', null, 'querySysRoleById', '0', '1473870264', '0'), ('9', '修改角色', '', '3', '3', '1', '3', 'sysRoleEditBtn', '', null, 'querySysRoleById', '0', '1473783724', '0'), ('10', '保存角色', '', '3', '4', '1', '3', 'sysRoleSaveBtn', '', null, 'saveSysRole', '0', '1473443318', '0'), ('11', '删除角色', '', '3', '5', '1', '3', 'sysRoleDelBtn', '', null, 'deleteSysRole', '0', '1473443347', '0'), ('12', '查询角色权限', '', '3', '6', '1', '3', 'sysRoleMenuViewBtn', '', null, 'queryAllSysMenu,selectMenuByRoleId', '0', '1473608555', '0'), ('13', '保存角色权限', '', '3', '7', '1', '3', 'sysRoleMenuSaveBtn', '', null, 'saveSysRoleMenu', '0', '1473608585', '0'), ('14', '查询用户列表', '', '2', '1', '1', '3', '', '', null, 'queryAllSysUser', '0', '1473787183', '0'), ('15', '新增用户', '', '2', '2', '1', '3', 'sysUserAddBtn', '', null, 'queryAllSysRole,selectRoleByUserId,querySysUserById', '0', '1473784520', '0'), ('16', '修改用户', '', '2', '3', '1', '3', 'sysUserEditBtn', '', null, 'queryAllSysRole,selectRoleByUserId,querySysUserById', '0', '1473442993', '0'), ('17', '保存用户', '', '2', '4', '1', '3', 'sysUserSaveBtn', '', null, 'saveSysUser', '0', '1473917939', '0'), ('18', '删除用户', '', '2', '5', '1', '3', 'sysUserDelBtn', '', null, 'deleteSysUser', '0', '1534822291', '0'), ('43', '修改密码', '/SysUserController/modifyPwdPage', '1', '5', '1', '2', '', '', null, 'modifyPwd', '0', '1534755226', '0'), ('54', '查看用户详情', '', '2', '6', '1', '3', 'sysUserViewBtn', '', null, 'queryAllSysRole,selectRoleByUserId,querySysUserById', '0', '1473785237', '0'), ('55', '查看角色详情', '', '3', '8', '1', '3', 'sysRoleViewBtn', '', null, 'querySysRoleById', '0', '1473911016', '0'), ('106', '区域管理1', '/jsp/sysManage/sysRegion/sysRegionList.jsp', '1', '5', '1', '2', '', '', null, 'sysRegionList.jsp,sysRegionRight.jsp,queryAllRegion.do,deleteSysregion.do,saveSysRegion.do', '1477573845', '1477573906', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_info` varchar(400) DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色状态',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建者ID',
  `create_time` int(11) NOT NULL DEFAULT '0' COMMENT '创建日期',
  `update_time` int(11) NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '用户角色', '角色13333adasda', '1', '0', '0', '1534496511'), ('3', '角色2', '', '1', '0', '0', '1534491593'), ('4', '角色3', 'asdasdasd', '1', '1', '1534491605', '1534491625'), ('5', 'hah ddd', 'asdasd', '1', '2', '1534502304', '1534502318');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='系统角色菜单关联表';

-- ----------------------------
--  Records of `sys_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES ('15', '1', '5'), ('16', '1', '6'), ('17', '1', '9'), ('18', '1', '10'), ('19', '1', '11'), ('20', '1', '12'), ('21', '1', '13'), ('9', '1', '14'), ('10', '1', '15'), ('11', '1', '16'), ('12', '1', '17'), ('13', '1', '18'), ('23', '1', '43'), ('14', '1', '54'), ('22', '1', '55'), ('6', '3', '5'), ('7', '3', '6'), ('8', '3', '9'), ('24', '5', '14'), ('25', '5', '16'), ('26', '5', '17');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(100) NOT NULL COMMENT '用户名',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `login_pwd` varchar(100) NOT NULL COMMENT '登录密码',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机号码',
  `phone_number` varchar(20) DEFAULT '' COMMENT '固定电话',
  `address` varchar(200) DEFAULT '' COMMENT '用户地址',
  `email` varchar(100) DEFAULT '' COMMENT 'email',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态（1-正常；2-已删除；3-无效）',
  `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `last_login_time` int(11) DEFAULT '0' COMMENT '最后一次登录时间',
  `create_time` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(11) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `identity` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户身份（1-超级管理员；2-店长；3-店员）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', '艾德敏', 'admin', '21232f297a57a5a743894a0e4a801fc3', '13951882433', '87878788', '江苏南京', '123@163.com', '1', '1', '1536544310', '1534485527', '0', '1'), ('2', '用户13333', 'user1', 'e10adc3949ba59abbe56e057f20f883e', '13951882433', '025-84661234', '江苏南京', 'terry@163.com', '1', '0', '1536507741', '0', '1534496533', '2'), ('3', '用户2', 'user2', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '1534495647', '3'), ('4', '用户3', 'user3', '21232f297a57a5a743894a0e4a801fc3', '3', '', '', '', '1', '0', '0', '0', '0', '3'), ('5', '用户4', 'user4', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('6', '用户5', 'user5', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('7', '用户6', 'user6', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('8', '用户7', 'user7', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('9', '用户8', 'user8', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('10', '用户91112121', 'user9', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '1536316018', '3'), ('11', '用户10', 'user10', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('12', '用户11', 'user11', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('13', '用户12', 'user12', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('14', '用户13', 'user13', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('15', '用户14', 'user14', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '1534497996', '3'), ('16', '用户15', 'user15', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('17', '用户16', 'user16', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('18', '用户17', 'user17', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '2', '0', '0', '0', '0', '3'), ('19', '用户18', 'user18', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('20', '用户19', 'user19', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('21', '用户20', 'user20', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('22', '用户21', 'user21', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('23', '用户22', 'user22', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('24', '用户23', 'user23', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '1', '0', '0', '0', '0', '3'), ('26', '哈哈', 'hhhh', 'e10adc3949ba59abbe56e057f20f883e', '13951882344', '025-22323323', '阿克苏建档立卡教室里的空间爱丽丝', 'www@163.com', '1', '1', '0', '1534495933', '1534495933', '3'), ('27', 'dasdasd', 'aaaa', 'e10adc3949ba59abbe56e057f20f883e', '13951882555', '', '', '', '2', '2', '0', '1534497864', '1534497891', '2');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('17', '2', '1'), ('11', '3', '4'), ('12', '25', '1'), ('13', '26', '3'), ('19', '27', '4');
COMMIT;

-- ----------------------------
--  Table structure for `test_table`
-- ----------------------------
DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(512) DEFAULT NULL,
  `simplified` mediumtext,
  `test_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `test_table`
-- ----------------------------
BEGIN;
INSERT INTO `test_table` VALUES ('3', 'title3', 'sim3', 'testname3'), ('4', 'title4', 'sim4', 'testname4');
COMMIT;

-- ----------------------------
--  Function structure for `getParentsMenuIdByMenuId`
-- ----------------------------
DROP FUNCTION IF EXISTS `getParentsMenuIdByMenuId`;
delimiter ;;
CREATE DEFINER=``@`` FUNCTION `getParentsMenuIdByMenuId`(rootId VARCHAR(5000)) RETURNS varchar(5000) CHARSET utf8
    READS SQL DATA
    COMMENT '向上递归查询所有父菜单节点'
BEGIN

    DECLARE sTemp VARCHAR(5000);

    DECLARE sTempChd VARCHAR(5000);

    SET sTemp = '$';

    SET sTempChd =cast(rootId as CHAR);

      WHILE sTempChd is not null DO

        SET sTemp = concat(sTemp,',',sTempChd);

        SELECT group_concat(parent_menu_id) INTO sTempChd FROM sys_menu where FIND_IN_SET(id,sTempChd)>0;

      END WHILE;

    RETURN sTemp;

  END
 ;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
