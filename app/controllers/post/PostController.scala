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
import persistence.post.dao.PostDAO
import persistence.spot.dao.SpotDAO
import persistence.post.model.Post
import persistence.post.model.Post.formForPostSearch
import model.site.post.SiteViewValuePostShow
import model.site.post.SiteViewValueHome

// 施設
//~~~~~~~~~~~~~~~~~~~~~
class PostController @javax.inject.Inject()(
         postDAO: PostDAO,
         spotDAO: SpotDAO,
         cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext


  def show(id: Post.Id) = Action.async { implicit request =>
    for {
      p <- postDAO.get(id)
      s <- spotDAO.get(p.get.spotId)
    } yield {
      val vv = SiteViewValuePostShow(
        layout = ViewValuePageLayout(id = request.uri),
        post = p.get,
        spot = s.get
      )
      Ok(views.html.site.post.show.Main(vv))
    }
  }

    /*
    formForPostSearch.bindFromRequest.fold(
      errors => {
        for {
          p <- postDAO.get(id)
          s <- spotDAO.get(p.get.spot_id)
        } yield {
          val vv = SiteViewValuePostShow(
            layout     = ViewValuePageLayout(id = request.uri),
            post   = p.get,
            spot = s.get
          )
          Ok(views.html.site.post.show.Main(vv))
        }
      },
      form   => {
        for {
          locSeq      <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
          facilitySeq <- form.locationIdOpt match {
            case Some(id) =>
              for {
                locations   <- daoLocation.filterByPrefId(id)
                facilitySeq <- facilityDao.filterByLocationIds(locations.map(_.id))
              } yield facilitySeq
            case None     => facilityDao.findAll
          }
        } yield {
          val vv = SiteViewValueFacilityList(
            layout     = ViewValuePageLayout(id = request.uri),
            location   = locSeq,
            facilities = facilitySeq
          )
          Ok(views.html.site.facility.list.Main(vv, formForFacilitySearch.fill(form)))
        }
      }
    )
  }*/

}
