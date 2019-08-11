/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.post.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import persistence.udb.model.User
import persistence.spot.model.Spot

// post情報
//~~~~~~~~~~~~~
case class Post(
                 id:        Option[Post.Id],                    // postId
                 title:     String,                             // 投稿タイトル
                 content:   String,                             // 投稿内容
                 image:     Option[String],                             // 画像をbase64でエンコード
                 userId:    User.Id,                            // 投稿したユーザーのid
                 spotId:    Spot.Id,                            // Spot.id
                 updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
                 createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
               )

// 施設検索
case class PostSearch(
                       query: Option[String]
                     )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Post {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewPost = Form(
    mapping(
      "title"     -> nonEmptyText,
      "content"   -> nonEmptyText,
      "image"     -> optional(text),
      "spotId"    -> longNumber,
      "userId"    -> longNumber,
    )(Function.untupled(
      t => Post(None, t._1, t._2, t._3, t._4, t._5)
    ))(Post.unapply(_).map(
      t => (t._2, t._3, t._4, t._5, t._6)
    ))
  )

  val formForPostSearch = Form(
  mapping(
    "locationId" -> optional(text),
  )(PostSearch.apply)(PostSearch.unapply)
  )
}

