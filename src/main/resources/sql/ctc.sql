/*
 Navicat Premium Data Transfer

 Source Server         : postgres_dev
 Source Server Type    : PostgreSQL
 Source Server Version : 90423
 Source Host           : 192.168.56.101:5432
 Source Catalog        : ctc
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90423
 File Encoding         : 65001

 Date: 02/09/2019 13:49:25
*/


-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_user_id_seq";
CREATE SEQUENCE sys_user_id_seq
  INCREMENT 1
  MINVALUE 1
  START 1
  CACHE 1;

DROP TABLE IF EXISTS "public"."user_info";
CREATE TABLE "public"."user_info" (
  "id"                    int4 NOT NULL                               DEFAULT nextval('sys_user_id_seq' :: regclass),
  "email"                 varchar(255) COLLATE "pg_catalog"."default" DEFAULT nextval('sys_user_id_seq' :: regclass),
  "username"              varchar(255) COLLATE "pg_catalog"."default",
  "department"            varchar(255) COLLATE "pg_catalog"."default",
  "role"                  varchar(255) COLLATE "pg_catalog"."default",
  "password"              varchar(255) COLLATE "pg_catalog"."default",
  "name"                  varchar(255) COLLATE "pg_catalog"."default",
  "lastPasswordResetDate" date
);

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE "public"."user_info"
  ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

/*
 Navicat Premium Data Transfer

 Source Server         : postgres_dev
 Source Server Type    : PostgreSQL
 Source Server Version : 90423
 Source Host           : 192.168.56.101:5432
 Source Catalog        : ctc
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90423
 File Encoding         : 65001

 Date: 02/09/2019 13:48:40
*/


-- ----------------------------
-- Table structure for sys_label
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_label_id_seq";
CREATE SEQUENCE sys_label_id_seq
  INCREMENT 1
  MINVALUE 1
  START 1
  CACHE 1;

DROP TABLE IF EXISTS "public"."sys_label";
CREATE TABLE "public"."sys_label" (
  "id"         int4 NOT NULL DEFAULT nextval('sys_label_id_seq' :: regclass),
  "label_name" varchar(255) COLLATE "pg_catalog"."default",
  "label_num"  int4
);
COMMENT ON COLUMN "public"."sys_label"."label_num"
IS '当前label的引用次数';

-- ----------------------------
-- Primary Key structure for table sys_label
-- ----------------------------
ALTER TABLE "public"."sys_label"
  ADD CONSTRAINT "sys_label_pkey" PRIMARY KEY ("id");

/*
 Navicat Premium Data Transfer

 Source Server         : postgres_dev
 Source Server Type    : PostgreSQL
 Source Server Version : 90423
 Source Host           : 192.168.56.101:5432
 Source Catalog        : ctc
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90423
 File Encoding         : 65001

 Date: 02/09/2019 13:48:50
*/


-- ----------------------------
-- Table structure for sys_labelmap
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_media_label_id_seq";
CREATE SEQUENCE sys_media_label_id_seq
  INCREMENT 1
  MINVALUE 1
  START 1
  CACHE 1;

DROP TABLE IF EXISTS "public"."sys_labelmap";
CREATE TABLE "public"."sys_labelmap" (
  "id"       int4 NOT NULL DEFAULT nextval('sys_media_label_id_seq' :: regclass),
  "media_id" int4,
  "label_id" int4
);

-- ----------------------------
-- Primary Key structure for table sys_labelmap
-- ----------------------------
ALTER TABLE "public"."sys_labelmap"
  ADD CONSTRAINT "sys_media_label_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table sys_labelmap
-- ----------------------------
ALTER TABLE "public"."sys_labelmap"
  ADD CONSTRAINT "label_id" FOREIGN KEY ("label_id") REFERENCES "public"."sys_label" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

/*
 Navicat Premium Data Transfer

 Source Server         : postgres_dev
 Source Server Type    : PostgreSQL
 Source Server Version : 90423
 Source Host           : 192.168.56.101:5432
 Source Catalog        : ctc
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90423
 File Encoding         : 65001

 Date: 02/09/2019 13:48:58
*/


-- ----------------------------
-- Table structure for sys_media
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_media_id_seq";
CREATE SEQUENCE sys_media_id_seq
  INCREMENT 1
  MINVALUE 1
  START 1
  CACHE 1;

DROP TABLE IF EXISTS "public"."sys_media";
CREATE TABLE "public"."sys_media" (
  "id"            int4 NOT NULL DEFAULT nextval('sys_media_id_seq' :: regclass),
  "media_name"    varchar(255) COLLATE "pg_catalog"."default",
  "file_size"     float8,
  "path"          varchar(255) COLLATE "pg_catalog"."default",
  "category"      varchar(255) COLLATE "pg_catalog"."default",
  "authority"     varchar(255) COLLATE "pg_catalog"."default",
  "create_time"   date,
  "owner"         varchar(255) COLLATE "pg_catalog"."default",
  "download_num"  int4,
  "view_num"      int4,
  "type"          varchar(255) COLLATE "pg_catalog"."default",
  "thumbnail_uri" varchar(255) COLLATE "pg_catalog"."default",
  "pin"           int4
);

-- ----------------------------
-- Primary Key structure for table sys_media
-- ----------------------------
ALTER TABLE "public"."sys_media"
  ADD CONSTRAINT "sys_media_pkey" PRIMARY KEY ("id");

/*
 Navicat Premium Data Transfer

 Source Server         : postgres_dev
 Source Server Type    : PostgreSQL
 Source Server Version : 90423
 Source Host           : 192.168.56.101:5432
 Source Catalog        : ctc
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90423
 File Encoding         : 65001

 Date: 02/09/2019 13:49:06
*/


-- ----------------------------
-- Table structure for sys_pin
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_pin_id_seq";
CREATE SEQUENCE sys_pin_id_seq
  INCREMENT 1
  MINVALUE 1
  START 1
  CACHE 1;

DROP TABLE IF EXISTS "public"."sys_pin";
CREATE TABLE "public"."sys_pin" (
  "id"       int4 NOT NULL DEFAULT nextval('sys_pin_id_seq' :: regclass),
  "media_id" int4,
  "end_date" date
);

-- ----------------------------
-- Primary Key structure for table sys_pin
-- ----------------------------
ALTER TABLE "public"."sys_pin"
  ADD CONSTRAINT "sys_pin_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table sys_pin
-- ----------------------------
ALTER TABLE "public"."sys_pin"
  ADD CONSTRAINT "media_id" FOREIGN KEY ("media_id") REFERENCES "public"."sys_media" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

/*
 Navicat Premium Data Transfer

 Source Server         : postgres_dev
 Source Server Type    : PostgreSQL
 Source Server Version : 90516
 Source Host           : 192.168.56.100:5432
 Source Catalog        : ctc
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90516
 File Encoding         : 65001

 Date: 16/09/2019 22:33:12
*/


-- ----------------------------
-- Table structure for sys_folder
-- ----------------------------

DROP SEQUENCE IF EXISTS "public"."sys_folder_id_seq";
CREATE SEQUENCE sys_folder_id_seq
  INCREMENT 1
  MINVALUE 1
  START 1
  CACHE 1;


DROP TABLE IF EXISTS "public"."sys_folder";
CREATE TABLE "public"."sys_folder" (
  "id"               int4 NOT NULL DEFAULT nextval('sys_folder_id_seq' :: regclass),
  "folder_name"      varchar(255) COLLATE "pg_catalog"."default",
  "create_time"      date,
  "last_update_time" date,
  "category"         varchar(255) COLLATE "pg_catalog"."default"
);

-- ----------------------------
-- Primary Key structure for table sys_folder
-- ----------------------------
ALTER TABLE "public"."sys_folder"
  ADD CONSTRAINT "sys_folder_pkey" PRIMARY KEY ("id");





