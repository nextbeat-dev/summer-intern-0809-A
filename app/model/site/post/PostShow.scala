/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.post

import model.component.util.ViewValuePageLayout
import persistence.post.model.Post
import persistence.spot.model.Spot
import play.api.libs.json._
import persistence.post.model.PostComment

// 表示: 施設一覧
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValuePostShow(
  layout:   ViewValuePageLayout,
  post: Post,
  spot: Spot,
  comments: Seq[PostComment]
)

case class SiteViewValueHome
(
  layout: ViewValuePageLayout,
  jsonStr: String
)

case class PostSpot(title: String, content: String, latitude: Double, longitude: Double, postId: Long)
object PostSpot {
  implicit val jsonWrites = Json.writes[PostSpot]

  def apply(spot: Spot, post: Post):PostSpot ={
    PostSpot(post.title, post.content, spot.latitude, spot.longitude, post.id.get)
  }
}