package co.com.ceiba.formats

import co.com.ceiba.commands.RegistrarUsuarioCommand
import play.api.libs.json.{Format, Json}

trait CommandFormats {

  implicit val registrarUsuarioCommand: Format[RegistrarUsuarioCommand] = Json.format[RegistrarUsuarioCommand]
}
