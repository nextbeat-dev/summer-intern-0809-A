package controllers.post

import java.io.File
import java.nio.file.Files
import java.util.Base64

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import model.site.post.{PostSpotForm, SiteViewValuePostSpot}
import persistence.post.model.Post
import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import persistence.udb.dao.UserDAO
import play.api.i18n.I18nSupport
import mvc.action.AuthenticationAction

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
  implicit val daoUser: UserDAO,
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
    * 新規登録
    */
  def application = (Action(parse.multipartFormData) andThen AuthenticationAction()).async { implicit request =>

    val postedImage = request.body.file("postImage")
    var imageData: Option[String] = null

    if(postedImage != None){
      val mimeType = postedImage.get.contentType.get
      val imageByte: Array[Byte] = Base64.getEncoder().encode(Files.readAllBytes(postedImage.get.ref.path))
      imageData = Some("data:" + mimeType + ";base64," + new String(imageByte))
    }

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
      postSpot   => {
        val newForm = postSpot.copy(userId = request.user.id)
        val newnewForm = newForm.copy(image = imageData)
        for {
          id <- spotDAO.add(newnewForm.toSpot)
          _  <- postDAO.add(newnewForm.toPost(id))
        } yield {
          // TODO: セッション追加処理
          Redirect("/home/")
        }
      }
    )
  }
}
