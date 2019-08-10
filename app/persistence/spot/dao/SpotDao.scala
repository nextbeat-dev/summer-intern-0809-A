package persistence.spot.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.spot.model.Spot

// DAO: ユーザ情報
//~~~~~~~~~~~~~~~~~~
class SpotDAO @javax.inject.Inject()(
                                      val dbConfigProvider: DatabaseConfigProvider
                                    ) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[SpotTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
    * Spotを追加する
    */
  def add(data: Spot) =
    db.run {
      data.id match {
        case None    => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }

  /**
    * spotを取得する
    */

  def get(id: Spot.Id) = {
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }
  }

  def filterByIds(ids: Seq[Spot.Id]) =
    db.run{
      slick.filter(_.id inSet(ids)).result
    }

  /**
    * spotを削除する
    */
  def delete(id: Spot.Id) =
    db.run {
      slick
        .filter(_.id === id)
        .delete
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class SpotTable(tag: Tag) extends Table[Spot](tag, "spot") {

    // Table's columns
    /* @1 */ def id        = column[Spot.Id]       ("id", O.PrimaryKey, O.AutoInc)  // POST ID
    /* @2 */ def address   = column[String]        ("address")           // spotの住所
    /* @3 */ def longitude = column[Double]        ("longitude")         // 経度
    /* @4 */ def latitude  = column[Double]        ("latitude")          // 緯度
    /* @5 */ def updatedAt = column[LocalDateTime] ("updated_at")        // データ更新日
    /* @6 */ def createdAt = column[LocalDateTime] ("created_at")        // データ作成日

    // The * projection of the table
    def * = (
      id.?, address, longitude, latitude, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Spot.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Spot.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
