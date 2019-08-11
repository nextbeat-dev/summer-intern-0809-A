-- pinのこと
--------------
CREATE TABLE "spot" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  --"name"        VARCHAR(255) NOT NULL,
  --"content"     TEXT         NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "longitude"   DOUBLE       NOT NULL,  -- 経度
  "latitude"    DOUBLE       NOT NULL,  -- 緯度
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('東京都杉並区', 139.650834, 35.706915);
INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('東京都新宿区', 139.701826, 35.683679);
INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('東京都中央区', 139.767145, 35.671720);
INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('北海道', 142.540049, 43.3040214);
INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('北海道', 142.4005825, 43.2918496);
INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('北海道', 142.4359786, 43.3411513);
INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('北海道', 142.3626772, 43.3233133);
INSERT INTO "spot" ("address", "longitude", "latitude") VALUES ('東京都新宿区', 139.703129, 35.696427);