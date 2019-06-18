package co.com.ceiba.aplicacion.commands

import co.com.ceiba.aplicacion.events.{ExitoUsuarioEvent, FalloEvent, UsuarioEliminadoEvent}
import co.com.ceiba.dominio.services.{EliminarUsuarioUseCase, RegistrarUsuarioUseCase}
import co.com.ceiba.dominio.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.Future

class CommandHandlerTest extends AsyncWordSpec with MustMatchers with MockitoSugar {

  val registarUsuarioUseCase: RegistrarUsuarioUseCase = mock[RegistrarUsuarioUseCase]
  val eliminarUsuarioUseCase: EliminarUsuarioUseCase = mock[EliminarUsuarioUseCase]

  val manejador = new CommandHandler(registarUsuarioUseCase, eliminarUsuarioUseCase)

  "CommandHandler" should {

    "manejar RegistrarUsuarioCommand correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      when(registarUsuarioUseCase.registrar(usuario.id, usuario.nombre, usuario.apellido, usuario.email)) thenReturn Future(usuario)

      manejador
        .manejarComando(
          RegistrarUsuarioCommand(
            usuario.id,
            usuario.nombre,
            usuario.apellido,
            usuario.email)).map(evento => evento must equal(ExitoUsuarioEvent(usuario)))

    }

    "manejar EliminarUsuarioCommand correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      when(eliminarUsuarioUseCase.eliminar(usuario.id)) thenReturn Future(usuario.id)

      manejador
        .manejarComando(
          EliminarUsuarioCommand(usuario.id)
        ).map(event => event must equal(UsuarioEliminadoEvent(usuario.id)))

    }

    "manejar Command no implementado correctamente" in {

      case class ComandoTest() extends Command

      manejador
        .manejarComando(
          ComandoTest()
        ).map(event => event must equal(FalloEvent(CommandHandler.COMANDO_NO_SOPORTADO)))

    }

    "manejar el fallo de cualquier ejecucion correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      case class ExcepcionTest() extends Exception

      when(registarUsuarioUseCase.registrar(usuario.id, usuario.nombre, usuario.apellido, usuario.email)) thenReturn Future.failed(ExcepcionTest())

      manejador
        .manejarComando(
          RegistrarUsuarioCommand(
            usuario.id,
            usuario.nombre,
            usuario.apellido,
            usuario.email)).map(event => event must equal(FalloEvent(ExcepcionTest().getLocalizedMessage)))

    }

  }
}
