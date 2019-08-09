-- postに対するコメント
--------------
CREATE TABLE "post_comment" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "content"     TEXT         NOT NULL,
  "post_id"     INT          NOT NULL,
  "user_id"     INT          NOT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;