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

// post_comment と　user をつなぐ中間テーブル likeに使えると思う
//~~~~~~~~~~~~~
case class UserPostComment(
                      id:               Option[UserPostComment.Id],         // Id
                      userId:           User.Id,                            // ユーザーのid
                      postCommentId:    PostComment.Id,                     // ポストのid
                      updatedAt:        LocalDateTime = LocalDateTime.now,  // データ更新日
                      createdAt:        LocalDateTime = LocalDateTime.now   // データ作成日

                            )
// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object UserPostComment {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewUserPostComment = Form(
    mapping(
      "userId"          -> longNumber,
      "postCommentId"   -> longNumber,
    )(Function.untupled(
      t => UserPostComment(None, t._1, t._2)
    ))(UserPostComment.unapply(_).map(
      t => (t._2, t._3)
    ))
  )
}