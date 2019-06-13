package co.com.ceiba.commands

import co.com.ceiba.events.{FalloEvent, OperacionUsuarioEvent}
import co.com.ceiba.services.{EliminarUsuarioUseCase, RegistrarUsuarioUseCase}
import co.com.ceiba.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

import scala.util.{Failure, Success}

class CommandHandlerTest extends PlaySpec with MockitoSugar {

  val registarUsuarioUseCase: RegistrarUsuarioUseCase = mock[RegistrarUsuarioUseCase]
  val eliminarUsuarioUseCase: EliminarUsuarioUseCase = mock[EliminarUsuarioUseCase]

  val manejador = new CommandHandler(registarUsuarioUseCase, eliminarUsuarioUseCase)

  "CommandHandler" should {

    "manejar RegistrarUsuarioCommand correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      when(registarUsuarioUseCase.registrar(usuario.id, usuario.nombre, usuario.apellido, usuario.email)) thenReturn Success(usuario)

      manejador
        .manejarComando(
          RegistrarUsuarioCommand(
            usuario.id,
            usuario.nombre,
            usuario.apellido,
            usuario.email)) must equal(OperacionUsuarioEvent(usuario))

    }

    "manejar EliminarUsuarioCommand correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      when(eliminarUsuarioUseCase.eliminar(usuario.id)) thenReturn Success(usuario)

      manejador
        .manejarComando(
          EliminarUsuarioCommand(usuario.id)
        ) must equal(OperacionUsuarioEvent(usuario))

    }

    "manejar Command no implementado correctamente" in {

      case class ComandoTest() extends Command

      manejador
        .manejarComando(
          ComandoTest()
        ) must equal(FalloEvent(manejador.COMANDO_NO_SOPORTADO))

    }

    "manejar el fallo de cualquier ejecucion correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      case class ExcepcionTest() extends Exception

      when(registarUsuarioUseCase.registrar(usuario.id, usuario.nombre, usuario.apellido, usuario.email)) thenReturn Failure(ExcepcionTest())

      manejador
        .manejarComando(
          RegistrarUsuarioCommand(
            usuario.id,
            usuario.nombre,
            usuario.apellido,
            usuario.email)) must equal(FalloEvent(ExcepcionTest().getLocalizedMessage))

    }

  }
}
