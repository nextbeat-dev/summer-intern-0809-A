package controllers.mypage

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.udb.dao.UserDAO
import persistence.post.dao.PostDAO
import persistence.udb.model.User
import model.component.util.ViewValuePageLayout
import model.site.mypage.SiteViewValueUserShow

// 施設
//~~~~~~~~~~~~~~~~~~~~~
class MypageController @javax.inject.Inject()(
  val userDao: UserDAO,
  val postDao: PostDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext


  /**
    * mypage
    */
  def show() = Action.async { implicit request =>

    for {
      user     <- userDao.get(1)
      postSeq  <- postDao.getByUserId(1)
    } yield {

      val vv = SiteViewValueUserShow(
        layout = ViewValuePageLayout(id = request.uri),
        user   = user.get,
        posts  = postSeq
      )

      Ok(views.html.site.mypage.show.Main(vv))
    }
  }

}
