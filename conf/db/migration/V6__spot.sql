-- pinのこと
--------------
CREATE TABLE "spot" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "name"        VARCHAR(255) NOT NULL,
  "content"     TEXT         NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "longitude"   DOUBLE       NOT NULL,  -- 経度
  "latitude"    DOUBLE       NOT NULL,  -- 緯度
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;