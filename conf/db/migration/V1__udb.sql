
-- ユーザ定義
--------------
CREATE TABLE "udb_user" (
  "id"         INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
  "name_first" VARCHAR(255) NOT     NULL,
  "name_last"  VARCHAR(255) NOT     NULL,
  "email"      VARCHAR(255) DEFAULT NULL,
  "updated_at" timestamp    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at" timestamp    NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ユーザ・パスワード
----------------------
CREATE TABLE "udb_user_password" (
  "id"         INT         NOT NULL PRIMARY KEY,
  "user_id"    INT         NOT NULL,
  "password"   TEXT        NOT NULL ,
  "updated_at" TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at" TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


INSERT INTO "udb_user" ("name_first", "name_last", "email") VALUES ('佐藤', '太郎', 'yukiyuki@gamail.com')