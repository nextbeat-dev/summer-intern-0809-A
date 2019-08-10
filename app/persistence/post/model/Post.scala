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
                 image:     String,                             // 画像をbase64でエンコード
                 spot_id:   Spot.Id,                            // Spot.id
                 user_id:   User.Id,                            // 投稿したユーザーのid
                 updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
                 createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
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
      "image"     -> text,
      "spotId"    -> longNumber,
      "userId"    -> longNumber,
    )(Function.untupled(
      t => Post(None, t._1, t._2, t._3, t._4, t._5)
    ))(Post.unapply(_).map(
      t => (t._2, t._3, t._4, t._5, t._6)
    ))
  )
}

