package co.com.ceiba.infraestructura.driver.api_rest.formats

import co.com.ceiba.dominio.usuario.Usuario
import play.api.libs.json.{Format, Json}


trait UsuarioFormat {
  implicit val userFormat: Format[Usuario] = Json.format[Usuario]
}
