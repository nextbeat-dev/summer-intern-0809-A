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

// post情報
// post と　user をつなぐ中間テーブル likeに使えると思う
//~~~~~~~~~~~~~
case class Post_user(
                 id:        Option[Post_user.Id],               // Id
                 user_id:   User.Id,                            // ユーザーのid
                 post_id:   Post.Id,                            // ポストのid
                 updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
                 createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
               )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Post_user {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewPost_user = Form(
    mapping(
      "user_id"   -> longNumber,
      "post_id"   -> longNumber,
    )(Function.untupled(
      t => Post_user(None, t._1, t._2)
    ))(Post_user.unapply(_).map(
      t => (t._2, t._3)
    ))
  )
}