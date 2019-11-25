package co.com.ceiba.infrastructure.driver.api_rest.formats

import co.com.ceiba.domain.user.User
import play.api.libs.json.{Format, Json}


trait UsuarioFormat {
  implicit val userFormat: Format[User] = Json.format[User]
}
