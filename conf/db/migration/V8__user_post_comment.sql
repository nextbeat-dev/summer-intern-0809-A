-- postに対するコメントに対するlike(likeTable)
--------------
CREATE TABLE "user_post_comment" (
  "id"                  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "post_comment_id"     INT          NOT NULL,
  "user_id"             INT          NOT NULL
  --"updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  --"created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;