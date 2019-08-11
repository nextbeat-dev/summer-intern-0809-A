
-- メールアドレスをユニークキー化
--------------

ALTER TABLE "udb_user"
    ADD CONSTRAINT uk_email UNIQUE ("email");