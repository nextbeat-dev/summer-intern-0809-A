/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.home

import model.component.util.ViewValuePageLayout
import mvc.action.AuthenticationAction
import persistence.udb.dao.UserDAO
import persistence.post.dao.PostDAO
import persistence.spot.dao.SpotDAO
import model.site.post.{PostSpot, SiteViewValueHome}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}

import scala.concurrent._
import persistence.post.model.Post._
import persistence.spot.model.Spot
import play.api.libs.json.Json


// 施設
//~~~~~~~~~~~~~~~~~~~~~
class HomeController @javax.inject.Inject()(
  implicit val daoUser: UserDAO,
  val daoPost: PostDAO,
  val daoSpot: SpotDAO,
  cc: MessagesControllerComponents,
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  def view = /*(Action andThen AuthenticationAction())*/Action.async { implicit request =>

    /**
     * request.user でログインユーザ情報が取得できる
     */

    for {
      postSeq <- daoPost.filterByTitle("天気の子")
      spotSeq <- daoSpot.filterByIds(postSeq.map(_.spotId))
    } yield {
      val postMap = postSeq.map(post => (post.spotId -> post)).toMap
      val psSeq = spotSeq.map(s => PostSpot(s, postMap(s.id.get)))

      val js = Json.toJson(psSeq)

      val vv = SiteViewValueHome(
        layout = ViewValuePageLayout(id = request.uri),
        jsonStr = Json.stringify(js)
      )

      Ok(views.html.site.home.Main(vv, formForPostSearch))
    }
  }

  /**
   * 検索
   */
  def search = Action.async { implicit request =>
    formForPostSearch.bindFromRequest.fold(
      errors => {
        for {
          postSeq <- daoPost.filterByTitle("天気の子")
          spotSeq <- daoSpot.filterByIds(postSeq.map(_.spotId))
        } yield {
          val postMap = postSeq.map(post => (post.spotId -> post)).toMap
          val psSeq = spotSeq.map(s => PostSpot(s, postMap(s.id.get)))

          val js = Json.toJson(psSeq)

          val vv = SiteViewValueHome(
            layout = ViewValuePageLayout(id = request.uri),
            jsonStr = Json.stringify(js)
          )

          Ok(views.html.site.home.Main(vv, errors))
        }
      },
      form => {
        for {
          postSeq <- daoPost.filterByTitle(form.query.get)
          spotSeq <- daoSpot.filterByIds(postSeq.map(_.spotId))
        } yield {
          val postMap = postSeq.map(post => (post.spotId -> post)).toMap
          val psSeq = spotSeq.map(s => PostSpot(s, postMap(s.id.get)))

          val js = Json.toJson(psSeq)

          val vv = SiteViewValueHome(
            layout = ViewValuePageLayout(id = request.uri),
            jsonStr = Json.stringify(js)
          )
          Ok(views.html.site.home.Main(vv, formForPostSearch.fill(form)))
        }
      }
    )
  }

}