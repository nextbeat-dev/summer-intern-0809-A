package persistence.post.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import persistence.udb.model.User

// postに付属するコメント情報
//~~~~~~~~~~~~~
case class Post_comment(
                 id:        Option[Post_comment.Id],            // postId
                 content:   String,                             // 投稿内容
                 post_id:   Post.Id,                            // Spot.id
                 user_id:   User.Id,                            // 投稿したユーザーのid
                 updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
                 createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
               )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Post_comment {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewPostComment = Form(
    mapping(
      "content"   -> nonEmptyText,
      "postId"    -> longNumber,
      "userId"    -> longNumber,
    )(Function.untupled(
      t => Post_comment(None, t._1, t._2, t._3)
    ))(Post_comment.unapply(_).map(
      t => (t._2, t._3, t._4)
    ))
  )
}