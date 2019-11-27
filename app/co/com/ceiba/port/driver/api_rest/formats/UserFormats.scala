package co.com.ceiba.port.driver.api_rest.formats

import co.com.ceiba.domain.user.{User, UserSeed}
import play.api.libs.json.{Format, Json}


trait UserFormats {
  implicit val userFormat: Format[User] = Json.format[User]
  implicit val userSeedFormat: Format[UserSeed] = Json.format[UserSeed]
}
