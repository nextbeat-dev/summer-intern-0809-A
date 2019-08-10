/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.post

import java.time.LocalDateTime

import model.component.util.ViewValuePageLayout
import model.site.app.NewUserForm
import persistence.post.model.Post
import persistence.spot.model.Spot
import persistence.udb.model.{User, UserPassword}
import play.api.data.Form
import play.api.data.Forms.{mapping, of}
import play.api.data.format.Formats.doubleFormat
import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import persistence.udb.model.User
import persistence.spot.model.Spot
import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location
import java.time.LocalDateTime
import persistence.udb.model.User
import persistence.udb.model.UserPassword
import play.api.data.Forms._
import play.api.data._

// 表示: 施設一覧
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValuePostSpot(
                                  layout:   ViewValuePageLayout,
                                  location: Seq[Location]
)

// 投稿情報
//~~~~~~~~~~~~~
case class PostSpotForm
(
  title:  String,                             // 作品名
  content: String,                             // 場所の説明
  image:  Option[String],                               // 画像
  address:     String,                             // 住所
  longitude:  Double,                             // 軽度
  latitude: Double,                               // 緯度
  updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
) {
  def toSpot = {
    Spot(None, address, longitude, latitude)
  }

  def toPost(id: Spot.Id) = {
    Post(None, title, content, image, id, id)
  }


}

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object PostSpotForm {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForPostSpot = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> text,
      "image" -> optional(text),
      "address" -> text,
      "longitude" -> of(doubleFormat),
      "latitude" -> of(doubleFormat)
    )(
      (t1, t2, t3, t4, t5, t6) => PostSpotForm(t1, t2, t3, t4, t5, t6)
    )(PostSpotForm.unapply(_).map(
      t => (t._1, t._2, t._3, t._4, t._5, t._6)
    ))
  )
}