package persistence.post.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.post.model.Post
import persistence.post.model.PostComment
import persistence.post.model.UserPostComment
import persistence.udb.model.User


// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class UserPostCommentDAO @javax.inject.Inject()(
                                             val dbConfigProvider: DatabaseConfigProvider
                                           ) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[UserPostCommentTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
    * Postに対するコメント情報を追加する
    */
  def add(data: UserPostComment) =
    db.run {
      data.id match {
        case None    => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }

  /**
    * 取得する
    */

  def getByUserId(id: User.Id) =
    db.run {
      slick
        .filter(_.userId === id)
        .result.headOption
    }

  def getByPostCommentId(id: PostComment.Id) =
    db.run {
      slick
        .filter(_.postCommentId === id)
        .result
    }
  /**
    * 削除する
    */
  def delete(id: UserPostComment.Id) =
    db.run {
      slick
        .filter(_.id === id)
        .delete
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class UserPostCommentTable(tag: Tag) extends Table[UserPostComment](tag, "user_post_comment") {

    // Table's columns
    /* @1 */ def id                   = column[PostComment.Id]        ("id", O.PrimaryKey, O.AutoInc)
    /* @2 */ def userId               = column[User.Id]               ("user_id")           // 投稿したユーザーのid
    /* @3 */ def postCommentId        = column[PostComment.Id]        ("post_comment_id")   // 対応するpostcommentのid
    /* @4 */ def updatedAt            = column[LocalDateTime]         ("updated_at")        // データ更新日
    /* @5 */ def createdAt            = column[LocalDateTime]         ("created_at")        // データ作成日

    // The * projection of the table
    def * = (
      id.?, userId, postCommentId, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (UserPostComment.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => UserPostComment.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}