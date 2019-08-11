package persistence.post.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.post.model.PostUser
import persistence.udb.model.User
import persistence.post.model.Post

// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class PostUserDAO @javax.inject.Inject()(
                                      val dbConfigProvider: DatabaseConfigProvider
                                    ) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[PostUserTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
    * Post_user情報を追加する
    */
  def add(data: PostUser) =
    db.run {
      data.id match {
        case None    => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }

  /**
    * post_userを取得する
    */

  def get(id: PostUser.Id) =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
    * userのidでフィルター
    */
  def getFilterByUserId(id: User.Id) =
    db.run {
      slick
        .filter(_.userId === id)
        .result
    }

  /**
    * postのidでフィルター
    */

  def getFilterByPostId(id: Post.Id) =
    db.run {
      slick
        .filter(_.postId === id)
        .result
    }

  /**
    * 関係性を削除する
    */
  def delete(id: PostUser.Id) =
    db.run {
      slick
        .filter(_.id === id)
        .delete
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class PostUserTable(tag: Tag) extends Table[PostUser](tag, "post_user") {

    // Table's columns
    /* @1 */ def id        = column[PostUser.Id]  ("id", O.PrimaryKey, O.AutoInc)
    /* @2 */ def userId    = column[User.Id]       ("user_id")           // user id
    /* @3 */ def postId    = column[Post.Id]       ("post_id")           // post id
    /* @4 */ def updatedAt = column[LocalDateTime] ("updated_at")        // データ更新日
    /* @5 */ def createdAt = column[LocalDateTime] ("created_at")        // データ作成日

    // The * projection of the table
    def * = (
      id.?, userId, postId, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (PostUser.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => PostUser.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
