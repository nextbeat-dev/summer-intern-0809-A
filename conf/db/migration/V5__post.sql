-- spotにくっつく詳細説明
--------------
CREATE TABLE "post" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "title"       VARCHAR(255) NOT NULL,
  "content"     TEXT         NOT NULL,
  "image"       LONGTEXT,  --今回はbase64でエンコードして保存　ストレージを使うことが望ましい
  "user_id"     INT          NOT NULL,
  "spot_id"     INT          NOT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

INSERT INTO "post" ("title", "content", "image", "user_id", "spot_id") VALUES ('君の名はの名シーン!!', '第二話のあの場面で、どう行動行動こづどうdvdjvcjh',  null, 1, 1);
INSERT INTO "post" ("title", "content", "image", "user_id", "spot_id") VALUES ('天気の子', '代々木会館',  null, 1, 2);
INSERT INTO "post" ("title", "content", "image", "user_id", "spot_id") VALUES ('天気の子', '朝日稲荷神社',  null, 1, 3);