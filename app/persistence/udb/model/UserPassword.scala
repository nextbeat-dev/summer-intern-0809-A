package persistence.udb.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime

// ユーザ情報
//~~~~~~~~~~~~~
case class UserPassword(
                 id:        Option[UserPassword.Id],
                 user_id:   User.Id,
                 password:  String,
                 updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
                 createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
               )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object UserPassword {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

}