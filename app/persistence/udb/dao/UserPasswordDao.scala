
package persistence.udb.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.udb.model.User
import persistence.udb.model.UserPassword

// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class UserPasswordDAO @javax.inject.Inject()(
                                      val dbConfigProvider: DatabaseConfigProvider
                                    ) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[UserPasswordTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
    * ユーザのpasswordを追加する
    */
  def add(data: UserPassword) =
    db.run {
      data.id match {
        case Some(_) => slick += data
        case None    => DBIO.failed(
          new IllegalArgumentException("The given object is not assigned id.")
        )
      }
    }

  /**
    * ユーザを削除した時 passwordも削除
    */
  def delete(id: User.Id) =
    db.run {
      slick
        .filter(_.user_id === id)
        .delete
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class UserPasswordTable(tag: Tag) extends Table[UserPassword](tag, "udb_user_password") {

    // Table's columns
    /* @1 */ def id        = column[User.Id]       ("id", O.PrimaryKey)  //ここいらんかもだけど一応
    /* @2 */ def user_id   = column[User.Id]       ("user_id")          // passwordと結びついているユーザーのid
    /* @3 */ def password  = column[String]        ("password")         // password hash下させて保存
    /* @4 */ def updatedAt = column[LocalDateTime] ("updated_at")       // データ更新日
    /* @5 */ def createdAt = column[LocalDateTime] ("created_at")       // データ作成日

    // The * projection of the table
    def * = (
      id.?, user_id, password, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (UserPassword.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => UserPassword.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
