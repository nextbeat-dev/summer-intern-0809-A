/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.app

import model.component.util.ViewValuePageLayout
import model.site.app.{ LoginForm, SiteViewValueAuthLogin }
import persistence.udb.dao.{ UserDAO, UserPasswordDAO }
import play.api.i18n.I18nSupport
import play.api.mvc.{ AbstractController, MessagesControllerComponents }
import com.github.t3hnar.bcrypt._

import scala.concurrent.Future

// 認証処理
//~~~~~~~~~~~~~~~~~~~~~
class AuthController @javax.inject.Inject()(
  val daoUser: UserDAO,
  val daoUserPassword: UserPasswordDAO,
  val cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
   * ページの表示
   */
  def login = Action { implicit request =>
    val vv = SiteViewValueAuthLogin(
      layout = ViewValuePageLayout(id = request.uri)
    )
    Ok(views.html.site.app.login.Main(vv, LoginForm.formLogin))
  }

  def loginCommit = Action.async { implicit request =>
    LoginForm.formLogin.bindFromRequest.fold(
      errors => {
        val vv = SiteViewValueAuthLogin(
          layout = ViewValuePageLayout(id = request.uri)
        )
        Future.successful(
          BadRequest(views.html.site.app.login.Main(vv, errors))
        )
      },
      form => {
        for {
          userOpt     <- daoUser.findByEmail(form.email)
          passwordOpt <- userOpt match {
            case Some(user) => daoUserPassword.get(user.id.get)
            case None => Future.successful(None)
          }
        } yield {
          passwordOpt match {
            case Some(password) =>
              if (form.password.isBcryptedSafe(password.password).getOrElse(false)) {
                Redirect("/home/")
                  .withSession(
                    request.session + ("user_id" -> password.id.get.toString)
                  )
              } else {
                val vv = SiteViewValueAuthLogin(
                  layout = ViewValuePageLayout(id = request.uri)
                )
                BadRequest(views.html.site.app.login.Main(vv, LoginForm.formLogin))
              }
            case None =>
              val vv = SiteViewValueAuthLogin(
                layout = ViewValuePageLayout(id = request.uri)
              )
              BadRequest(views.html.site.app.login.Main(vv, LoginForm.formLogin))
          }

        }
      }
    )
  }
}
