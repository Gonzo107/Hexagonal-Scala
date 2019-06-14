package co.com.ceiba.commands

import co.com.ceiba.events.{Event, ExitoUsuarioEvent, FalloEvent, UsuarioEliminadoEvent}
import co.com.ceiba.services.{EliminarUsuarioUseCase, RegistrarUsuarioUseCase}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}


class CommandHandler @Inject()(registrarUsuarioUseCase: RegistrarUsuarioUseCase, eliminarUsuarioUseCase: EliminarUsuarioUseCase)
                              (implicit ec: ExecutionContext) {

  val COMANDO_NO_SOPORTADO = "Operacion no soportada"

  def manejarComando(comando: Command): Future[Event] = {

    (comando match {
      case RegistrarUsuarioCommand(id, nombre, apellido, email) =>
        registrarUsuarioUseCase.registrar(id, nombre, apellido, email).map(ExitoUsuarioEvent)

      case EliminarUsuarioCommand(id) => eliminarUsuarioUseCase.eliminar(id).map(UsuarioEliminadoEvent)

      case _ => Future(FalloEvent(COMANDO_NO_SOPORTADO))
    }).recover({
      case error => FalloEvent(error.getLocalizedMessage)
    })
  }

}
