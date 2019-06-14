package co.com.ceiba.formats

import co.com.ceiba.usuario.Usuario
import play.api.libs.json.{Json, Writes}


trait UsuarioFormat {
  implicit val userFormat: Writes[Usuario] = Json.writes[Usuario]
}
