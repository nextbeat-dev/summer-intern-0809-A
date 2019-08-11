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
case class PostUser(
                      id:        Option[PostUser.Id], // Id
                      userId:    User.Id, // ユーザーのid
                      postId:    Post.Id, // ポストのid
               )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object PostUser {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewPost_user = Form(
    mapping(
      "userId"   -> longNumber,
      "postId"   -> longNumber,
    )(Function.untupled(
      t => PostUser(None, t._1, t._2)
    ))(PostUser.unapply(_).map(
      t => (t._2, t._3)
    ))
  )
}