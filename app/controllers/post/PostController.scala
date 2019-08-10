/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.post

import model.component.util.ViewValuePageLayout
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import scala.concurrent._

// 施設
//~~~~~~~~~~~~~~~~~~~~~
class PostController @javax.inject.Inject()(
         cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /*
  def show(id: Long) = Action.async { implicit request =>
    Future {
      Ok(views.html.site.home.Main(ViewValuePageLayout(id = request.uri)))
    }
  }
  */
}
