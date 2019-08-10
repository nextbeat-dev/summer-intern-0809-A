package persistence.spot.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import play.api.data.format.Formats._

// post情報
//~~~~~~~~~~~~~
case class Spot(
                 id:        Option[Spot.Id],                    // spotId
                 address:   String,                             // spotの住所
                 longitude: Double,                             // 経度
                 latitude:  Double,                             // 緯度
                 updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
                 createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
               )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Spot {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewSpot = Form(
    mapping(
      "address"     -> text,
      "longitude"   -> of(doubleFormat),
      "latitude"    -> of(doubleFormat),
    )(Function.untupled(
      t => Spot(None, t._1, t._2, t._3)
    ))(Spot.unapply(_).map(
      t => (t._2, t._3, t._4)
    ))
  )
}

