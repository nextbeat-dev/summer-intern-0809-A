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
import mvc.action.AuthenticationAction
import persistence.post.dao.PostDAO
import persistence.spot.dao.SpotDAO
import persistence.udb.dao.UserDAO
import persistence.post.model.Post
import model.site.post.SiteViewValuePostShow
import persistence.post.model.PostComment.formForNewPostComment
import persistence.post.dao.PostCommentDAO

// 施設
//~~~~~~~~~~~~~~~~~~~~~
class PostController @javax.inject.Inject()(
         postDAO: PostDAO,
         spotDAO: SpotDAO,
         implicit val userDao: UserDAO,
         postCommentDao: PostCommentDAO,
         cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext


  //commentのformも
  def show(id: Post.Id) = Action.async { implicit request =>
    for {
      p <- postDAO.get(id)
      s <- spotDAO.get(p.get.spot_id)
      comment <- postCommentDao.getFilterByPostId(id)
    } yield {
      val vv = SiteViewValuePostShow(
        layout = ViewValuePageLayout(id = request.uri),
        post   = p.get,
        spot   = s.get,
        comments = comment
      )
      Ok(views.html.site.post.show.Main(vv, formForNewPostComment))
    }
  }

  def create(id: Post.Id) = (Action andThen AuthenticationAction()) { implicit request =>
    println("#############################")
    formForNewPostComment.bindFromRequest.fold(
      errors => {
        println("errorerrorerror")
        Redirect(s"/post/${id.toString}")
      },
      postComment => {
        val newPostComment = postComment.copy(userId = request.user.id)
        postCommentDao.add(newPostComment)
        Redirect(s"/post/${id.toString}")
      }
    )
  }

}
