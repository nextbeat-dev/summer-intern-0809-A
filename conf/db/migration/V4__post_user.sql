
-- postとユーザーを結びつける中間テーブル(likeTable)
--------------
CREATE TABLE "post_user" (
  "id"         INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
  "user_id"    INT          NOT     NULL,
  "post_id"    INT          NOT     NULL
  --"updated_at" timestamp    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  --"created_at" timestamp    NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;