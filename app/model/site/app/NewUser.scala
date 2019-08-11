/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.app

import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location
import java.time.LocalDateTime
import persistence.udb.model.User
import persistence.udb.model.UserPassword
import play.api.data.Forms._
import play.api.data._
import com.github.t3hnar.bcrypt._

// 登録: 新規ユーザー
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueNewUser(
  layout:   ViewValuePageLayout,
  location: Seq[Location]
)

// ユーザ情報
//~~~~~~~~~~~~~
case class NewUserForm
(
  nameLast:  String,                             // 名前 (姓)
  nameFirst: String,                             // 名前 (名)
  email:     String,                             // メールアドレス(重複あり)
  password:  String,
  updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
){
  def toUser ={
    User(None, nameLast, nameFirst, email)
  }

  def toUserPassword(id: User.Id) = {
    UserPassword(Some(id), id, password.bcrypt)
  }
}

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object NewUserForm {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewUser = Form(
    mapping(
      "nameLast"  -> nonEmptyText,
      "nameFirst" -> nonEmptyText,
      "email"     -> email,
      "password"  -> nonEmptyText,
    )(Function.untupled(
      t => NewUserForm(t._1, t._2, t._3, t._4)
    ))(NewUserForm.unapply(_).map(
      t => (t._1, t._2, t._3, t._4)
    ))
  )
}