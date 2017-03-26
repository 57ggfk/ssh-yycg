/*
Navicat Oracle Data Transfer
Oracle Client Version : 12.1.0.1.0

Source Server         : server
Source Server Version : 100200
Source Host           : 192.168.122.249:1521
Source Schema         : H37_TEAM1

Target Server Type    : ORACLE
Target Server Version : 100200
File Encoding         : 65001

Date: 2016-12-24 09:59:03
*/


-- ----------------------------
-- Table structure for SYS_PERMISSION
-- ----------------------------
DROP TABLE "H37_TEAM1"."SYS_PERMISSION";
CREATE TABLE "H37_TEAM1"."SYS_PERMISSION" (
"ID" VARCHAR2(64 BYTE) NOT NULL ,
"NAME" VARCHAR2(64 BYTE) NOT NULL ,
"PARENTID" VARCHAR2(32 BYTE) NOT NULL ,
"URL" VARCHAR2(256 BYTE) NULL ,
"ICON" VARCHAR2(128 BYTE) NULL ,
"SHOWORDER" VARCHAR2(64 BYTE) NULL ,
"ISUSED" CHAR(1 BYTE) NOT NULL ,
"ISMENU" CHAR(1 BYTE) NULL ,
"PLEVEL" CHAR(1 BYTE) NULL ,
"PCODE" VARCHAR2(64 BYTE) NULL ,
"VCHAR1" VARCHAR2(64 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "H37_TEAM1"."SYS_PERMISSION" IS '系统权限表';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."ID" IS '主键id';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."NAME" IS '模块名称';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."PARENTID" IS '父模块id';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."URL" IS '功能访问url';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."ICON" IS '模块图标';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."SHOWORDER" IS '显示顺序，用于菜单排列顺序';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."ISUSED" IS '状态标记： 1:使用， 0：不使用';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."ISMENU" IS '状态标记： 1:是菜单，0：不是菜单';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."PLEVEL" IS '层级';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."PCODE" IS '权限标识代码';
COMMENT ON COLUMN "H37_TEAM1"."SYS_PERMISSION"."VCHAR1" IS '备注字段';

-- ----------------------------
-- Records of SYS_PERMISSION
-- ----------------------------
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.02.04.', '用户删除', '00.06.02.', null, null, '00.06.02.04.', '1', '0', '3', 'use', 'del');
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.03.', '系统配置', '00.06.', '/system/systems.action', 'icon-log', '00.06.03.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.', '系统管理', '00.', null, 'icon-sys', null, '1', '1', '1', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('04.01.', '创建采购单', '04.', '/cgd/add.action', 'icon-log', '04.01.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('04.02.', '采购单维护', '04.', '/cgd/list.action', 'icon-log', '04.02.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('04.03.', '审核采购单', '04.', '/cgd/checklist.action', 'icon-log', '04.03.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('04.04.', '受理采购单', '04.', '/cgd/disposelist.action', 'icon-log', '04.04.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('03.01.', '药品目录导入', '03.', '/ypml/importypxx.action', 'icon-log', '03.02.', '1', '1', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('03.02.', '药品目录导出', '03.', '/ypml/exportypxx.action', 'icon-log', '03.01.', '1', '1', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('05.', '统计分析', '00.', null, 'icon-sys', '05.', '1', '1', '1', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('03.', '药品管理', '00.', 'ccc', 'icon-sys', null, '1', '1', '1', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('04.', '采购单管理', '00.', null, 'icon-sys', '04.', '1', '1', '1', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('03.03.', '药品目录同步', '03.', '/ypml/syncypxx.action', 'icon-log', '03.03.', '1', '1', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('05.03.', '按药品统计', '05.', '/analyze/drugyzreaUI.action', 'icon-log', '05.03.', '1', '1', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('05.02.', '按区域统计', '05.', '/business/analyze/echartSumbyarea.jsp', 'icon-log', '05.02.', '1', '1', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.02.01.', '用户查询', '00.06.02.', null, null, '00.06.02.01.', '1', '0', '3', 'user:queryuser', null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.02.02.', '用户添加', '00.06.02.', null, null, '00.06.02.02.', '1', '0', '3', 'user:add', null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.02.03.', '用户修改', '00.06.02.', null, null, '00.06.02.03.', '1', '0', '3', 'user:edit', null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('05.01.', '交易明细查询', '05.', '/bussinessdetail/bussinessDetailUI.action', 'icon-log', '05.01.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.02.', '用户管理', '00.06.', '/user/queryuser.action', 'icon-log', '00.06.02.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.04.', '角色授权', '00.06.', '/perm/rolelist.action', 'icon-log', '00.06.04.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.06.05.', '权限管理', '00.06.', '/perm/permList.action', 'icon-log', '00.06.05.', '1', '0', '2', null, null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('00.05.', 'test', '00.', 'dddd', null, '00.05.', '1', '1', '1', 'dd', null);
INSERT INTO "H37_TEAM1"."SYS_PERMISSION" VALUES ('testt', 'tttt', 'tes', 'ttttt', 'tt', 't', '1', '0', 'a', 'a', 'a');

-- ----------------------------
-- Indexes structure for table SYS_PERMISSION
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_PERMISSION
-- ----------------------------
ALTER TABLE "H37_TEAM1"."SYS_PERMISSION" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "H37_TEAM1"."SYS_PERMISSION" ADD CHECK ("NAME" IS NOT NULL);
ALTER TABLE "H37_TEAM1"."SYS_PERMISSION" ADD CHECK ("PARENTID" IS NOT NULL);
ALTER TABLE "H37_TEAM1"."SYS_PERMISSION" ADD CHECK ("ISUSED" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_PERMISSION
-- ----------------------------
ALTER TABLE "H37_TEAM1"."SYS_PERMISSION" ADD PRIMARY KEY ("ID");
