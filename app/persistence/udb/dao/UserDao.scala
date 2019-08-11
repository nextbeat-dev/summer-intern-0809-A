/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.udb.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.udb.model.User

// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class UserDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[UserTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * ユーザ情報を追加する
   */
  def add(data: User): Future[User.Id] =
    db.run {
      data.id match {
        case None    => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }

  def get(id: User.Id) =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  def findByEmail(email: String) =
    db.run {
      slick
        .filter(_.email === email)
        .result.headOption
    }

  /**
    * ユーザを削除する
    */
  def delete(id: User.Id) =
    db.run {
      slick
        .filter(_.id === id)
        .delete
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class UserTable(tag: Tag) extends Table[User](tag, "udb_user") {

    // Table's columns
    /* @1 */ def id        = column[User.Id]       ("id", O.PrimaryKey, O.AutoInc)  // ユーザID
    /* @2 */ def nameFirst = column[String]        ("name_first")        // 名前 (姓)
    /* @3 */ def nameLast  = column[String]        ("name_last")         // 名前 (名)
    /* @4 */ def email     = column[String]        ("email")             // メールアドレス
    /* @5 */ def updatedAt = column[LocalDateTime] ("updated_at")        // データ更新日
    /* @6 */ def createdAt = column[LocalDateTime] ("created_at")        // データ作成日

    // The * projection of the table
    def * = (
      id.?, nameFirst, nameLast, email, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (User.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => User.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
