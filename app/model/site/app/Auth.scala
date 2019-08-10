/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.app

import model.component.util.ViewValuePageLayout
import play.api.data.Forms._
import play.api.data._

// ログイン
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueAuthLogin(
  layout: ViewValuePageLayout,
)

// -[ ログインフォーム]-----------------------------------
case class LoginForm(
  email:    String,
  password: String,
)

object LoginForm {

  val formLogin = Form(
    mapping(
      "email"    -> nonEmptyText,
      "password" -> nonEmptyText
    )(Function.untupled(
      t => LoginForm(t._1, t._2)
    ))(LoginForm.unapply(_).map(
      t => (t._1, t._2)
    ))
  )
}
