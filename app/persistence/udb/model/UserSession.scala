/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

/* 使わないでーす */
package persistence.udb.model

import java.time.LocalDateTime

// ユーザ情報
//~~~~~~~~~~~~~
case class UserSession(
  id:        User.Id,                             // ユーザID
  token:     String,                              // トークン
  exprity:   LocalDateTime,                       // 有効期限
  updatedAt: LocalDateTime = LocalDateTime.now,   // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now    // データ作成日
) {
  lazy val isValid = exprity.isBefore(LocalDateTime.now)
}

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object UserSession {
}

