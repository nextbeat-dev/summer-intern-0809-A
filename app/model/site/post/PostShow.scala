/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.post

import model.component.util.ViewValuePageLayout
import persistence.post.model.Post
import persistence.spot.model.Spot

// 表示: 施設一覧
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValuePostShow(
  layout:   ViewValuePageLayout,
  post: Post,
  spot: Spot
)
