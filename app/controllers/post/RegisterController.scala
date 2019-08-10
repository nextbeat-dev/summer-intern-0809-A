package controllers.post

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import model.site.post.{PostSpotForm, SiteViewValuePostSpot}
import persistence.post.model.Post
import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import play.api.i18n.I18nSupport

import scala.concurrent._
import persistence.post.dao.PostDAO
import persistence.spot.dao.SpotDAO
import persistence.post.model.Post
import persistence.geo.model.Location
import model.site.post.SiteViewValuePostShow


class RegisterController @javax.inject.Inject()(
  val daoLocation: LocationDAO,
  val postDAO: PostDAO,
  val spotDAO: SpotDAO,
  cc: MessagesControllerComponents
  ) extends AbstractController(cc) with I18nSupport {
    implicit lazy val executionContext = defaultExecutionContext

  /**
    * 聖地の新規投稿
    */
  def register = Action.async { implicit request =>
    for {
      locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
    } yield {
      val vv = SiteViewValuePostSpot(
        layout   = ViewValuePageLayout(id = request.uri),
        location = locSeq
      )
      Ok(views.html.site.post.register.Main(vv, PostSpotForm.formForPostSpot))
    }
  }

  /**
    * 新規ユーザの登録
    */
  def application = Action.async { implicit request =>
    PostSpotForm.formForPostSpot.bindFromRequest.fold(
      errors => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        } yield {
          val vv = SiteViewValuePostSpot(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq
          )
          BadRequest(views.html.site.post.register.Main(vv, errors))
        }
      },
      post   => {
        for {
          id <- spotDAO.add(post.toSpot)
          _  <- postDAO.add(post.toPost(id))
        } yield {
          // TODO: セッション追加処理
          Redirect("/home/")
        }
      }
    )
  }
}
