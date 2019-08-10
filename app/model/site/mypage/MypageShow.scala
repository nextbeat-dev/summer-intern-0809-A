/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.mypage

import model.component.util.ViewValuePageLayout
import persistence.udb.model.User
import persistence.post.model.Post

// 表示: 施設一覧
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueUserShow(
   layout:  ViewValuePageLayout,
   user:    User,
   posts:   Seq[Post]
)
