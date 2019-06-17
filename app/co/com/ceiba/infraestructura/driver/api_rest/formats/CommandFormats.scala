package co.com.ceiba.infraestructura.driver.api_rest.formats

import co.com.ceiba.aplicacion.commands.RegistrarUsuarioCommand
import play.api.libs.json.{Format, Json}

trait CommandFormats {

  implicit val registrarUsuarioCommand: Format[RegistrarUsuarioCommand] = Json.format[RegistrarUsuarioCommand]
}
