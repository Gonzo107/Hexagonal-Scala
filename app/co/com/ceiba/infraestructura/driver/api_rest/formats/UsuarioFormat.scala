package co.com.ceiba.infraestructura.driver.api_rest.formats

import co.com.ceiba.dominio.usuario.Usuario
import play.api.libs.json.{Json, Writes}


trait UsuarioFormat {
  implicit val userFormat: Writes[Usuario] = Json.writes[Usuario]
}
