package co.com.ceiba.infraestructura.driver.api_rest.controllers

import co.com.ceiba.aplicacion.commands.{CommandHandler, EliminarUsuarioCommand, RegistrarUsuarioCommand}
import co.com.ceiba.aplicacion.events.{ExitoUsuarioEvent, FalloEvent, UsuarioEliminadoEvent}
import co.com.ceiba.dominio.services.ConsultarUsuariosUseCase
import co.com.ceiba.dominio.usuario.Usuario
import co.com.ceiba.infraestructura.driver.api_rest.formats.{CommandFormats, UsuarioFormat}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json.toJson
import play.api.mvc._

import scala.concurrent.ExecutionContext

class UsuariosController @Inject()(cc: ControllerComponents,
                                   commandHandler: CommandHandler,
                                   consultarUsuariosUseCase: ConsultarUsuariosUseCase)
                                  (implicit ec: ExecutionContext)
  extends AbstractController(cc)
    with UsuarioFormat
    with CommandFormats {


  def getAll = Action.async {

    consultarUsuariosUseCase.consultarTodos().map({
      case result: Seq[Usuario] => Ok(toJson(result))
      case Nil => NotFound
    })

  }

  def getById(id: Usuario.IdUsuario) = Action.async {

    consultarUsuariosUseCase.consultarPorId(id)
      .map(usuario => Ok(toJson(usuario)))
      .recover({ case error => NotFound(error.getLocalizedMessage) })
  }

  def create = Action(parse.json[RegistrarUsuarioCommand]).async { request =>

    commandHandler.manejarComando(request.body).map({
      case ExitoUsuarioEvent(usuario) => Created(toJson(usuario))
      case FalloEvent(e) => BadRequest(e)
    })


  }

  def delete(id: Usuario.IdUsuario) = Action.async {

    commandHandler.manejarComando(EliminarUsuarioCommand(id)).map({
      case UsuarioEliminadoEvent(idEliminado) => Ok(toJson(idEliminado))
      case FalloEvent(fallo) => BadRequest(fallo)
    })

  }

}
