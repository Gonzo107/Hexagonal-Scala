package co.com.ceiba.commands

import co.com.ceiba.events.{Event, FalloEvent, OperacionUsuarioEvent}
import co.com.ceiba.services.{EliminarUsuarioUseCase, RegistrarUsuarioUseCase}
import co.com.ceiba.usuario.Usuario

import scala.util.{Failure, Success, Try}


class CommandHandler(registrarUsuarioUseCase: RegistrarUsuarioUseCase, eliminarUsuarioUseCase: EliminarUsuarioUseCase) {

  val COMANDO_NO_SOPORTADO = "Operacion no soportada"

  def manejarComando(comando: Command): Event = {

    comando match {
      case RegistrarUsuarioCommand(id, nombre, apellido, email) =>
        ejecutarOperacionUsuario(() => registrarUsuarioUseCase.registrar(id, nombre, apellido, email))
      case EliminarUsuarioCommand(id) => ejecutarOperacionUsuario(() => eliminarUsuarioUseCase.eliminar(id))

      case _ => FalloEvent(COMANDO_NO_SOPORTADO)
    }
  }

  private def ejecutarOperacionUsuario(commandExecutor: () => Try[Usuario]): Event = {

    commandExecutor() match {
      case Success(usuario) => OperacionUsuarioEvent(usuario)
      case Failure(exception) => FalloEvent(exception.getLocalizedMessage)
    }

  }
}
