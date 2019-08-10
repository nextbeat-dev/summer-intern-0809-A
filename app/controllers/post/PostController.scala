/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.post

import model.component.util.ViewValuePageLayout
import model.site.app.{NewUserForm, SiteViewValueNewUser}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}

import scala.concurrent._
import persistence.post.dao.PostDAO
import persistence.spot.dao.SpotDAO
import persistence.post.model.Post
import model.site.post.SiteViewValuePostShow
import persistence.geo.model.Location


// 施設
//~~~~~~~~~~~~~~~~~~~~~
class PostController @javax.inject.Inject()(
         postDAO: PostDAO,
         spotDAO: SpotDAO,
         cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext


  def show(id: Post.Id) = Action.async { implicit request =>
    for {
      p <- postDAO.get(id)
      s <- spotDAO.get(p.get.spot_id)
    } yield {
      val vv = SiteViewValuePostShow(
        layout     = ViewValuePageLayout(id = request.uri),
        post   = p.get,
        spot = s.get
      )
      Ok(views.html.site.post.show.Main(vv))
    }
  }
}
