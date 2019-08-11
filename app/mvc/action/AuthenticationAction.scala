package mvc.action

import persistence.udb.dao.UserDAO
import persistence.udb.model.User
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.{ ExecutionContext, Future }

case class UserRequest[A](
  user:    User,
  request: Request[A]
) extends WrappedRequest[A](request)

case class AuthenticationAction()(implicit
  val executionContext: ExecutionContext,
  implicit val daoUser: UserDAO
) extends ActionRefiner[Request, UserRequest] {

  protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = {
    val sUserIdOpt = request.session.get("user_id")
    sUserIdOpt match {
      case None          => Future.successful(Left(Redirect("/login", 301)))
      case Some(sUserId) => for {
        userOpt <- daoUser.get(sUserId.toLong)
      } yield
        userOpt match {
          case Some(user) =>
            val userRequest = UserRequest(user, request)
            Right(userRequest)
          case None => Left(Redirect("/login", 301))
        }
    }
  }
}