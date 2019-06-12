package co.com.ceiba.commands

import co.com.ceiba.events.{Event, FalloEvent, OperacionUsuarioEvent}
import co.com.ceiba.services.RegistrarUsuarioUseCase
import co.com.ceiba.usuario.Usuario

import scala.util.{Failure, Success, Try}


class CommandHandler(registrarUsuarioUseCase: RegistrarUsuarioUseCase) {

  def manejarComando(comando: Command): Event = {

    comando match {
      case RegistrarUsuarioCommand(id, nombre, apellido, email) =>
        ejecutarOperacionUsuario(registrarUsuarioUseCase.registrar(id, nombre, apellido, email))

    }
  }

  def ejecutarOperacionUsuario(result: Try[Usuario]): Event = {

    result match {
      case Success(usuario) => OperacionUsuarioEvent(usuario)
      case Failure(exception) => FalloEvent(exception.getLocalizedMessage)
    }

  }
}
