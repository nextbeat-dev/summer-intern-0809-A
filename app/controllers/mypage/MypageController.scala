package controllers.mypage

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.udb.dao.UserDAO
import persistence.post.dao.PostDAO
// ユーザー認証のやつ
import mvc.action.AuthenticationAction
import model.component.util.ViewValuePageLayout
import model.site.mypage.SiteViewValueUserShow

// 施設
//~~~~~~~~~~~~~~~~~~~~~
class MypageController @javax.inject.Inject()(
  implicit val daoUser: UserDAO,
  val postDao: PostDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext


  /**
    * mypage
    */
   def show = (Action andThen AuthenticationAction()).async { implicit request =>
  //def show() = Action.async { implicit request =>
    val user = request.user
    for {
      //Some(user) <- daoUser.get(1)
      postSeq    <- postDao.getByUserId(user.id.get)
    } yield {

      val vv = SiteViewValueUserShow(
        layout = ViewValuePageLayout(id = request.uri),
        user   = user,
        posts  = postSeq
      )

      Ok(views.html.site.mypage.show.Main(vv))
    }
  }

}
