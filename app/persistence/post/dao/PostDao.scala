package persistence.post.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.post.model.Post
import persistence.udb.model.User
import persistence.spot.model.Spot

// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class PostDAO @javax.inject.Inject()(
                                      val dbConfigProvider: DatabaseConfigProvider
                                    ) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[PostTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
    * Post情報を追加する
    */
  def add(data: Post) =
    db.run {
      data.id match {
        case None    => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }

  /**
    * postを取得する
    */

  def get(id: Post.Id): Future[Option[Post]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
  }

  def getBySpotId(id: Spot.Id) =
    db.run {
      slick
        .filter(_.spotId === id)
        .result.headOption
    }

  def getByUserId(id: User.Id) =
    db.run {
      slick
        .filter(_.userId === id)
        .result.headOption
    }

  def filterByTitle(title: String) =
    db.run{
      slick.filter(_.title === title)
        .result
    }

  /**
    * postを削除する
    */
  def delete(id: Post.Id) =
    db.run {
      slick
        .filter(_.id === id)
        .delete
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class PostTable(tag: Tag) extends Table[Post](tag, "post") {

    // Table's columns
    /* @1 */ def id        = column[Post.Id]       ("id", O.PrimaryKey, O.AutoInc)  // POST ID
    /* @2 */ def title     = column[String]        ("title")             // post title
    /* @3 */ def content   = column[String]        ("content")           // post content
    /* @4 */ def image     = column[String]        ("image")             // base64でエンコードしたimage
    /* @5 */ def userId    = column[User.Id]       ("user_id")           // 投稿したユーザーのid
    /* @6 */ def spotId    = column[Spot.Id]       ("spot_id")           // 紐づいているspotのID
    /* @7 */ def updatedAt = column[LocalDateTime] ("updated_at")        // データ更新日
    /* @8 */ def createdAt = column[LocalDateTime] ("created_at")        // データ作成日

    // The * projection of the table
    def * = (
      id.?, title, content, image.?, userId, spotId, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Post.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Post.unapply(v).map(_.copy(
        _7 = LocalDateTime.now
      ))
    )
  }
}
