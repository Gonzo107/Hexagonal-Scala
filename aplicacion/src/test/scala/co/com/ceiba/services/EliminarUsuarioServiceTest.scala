package co.com.ceiba.services

import co.com.ceiba.exceptions.NoExisteException
import co.com.ceiba.usuario.UsuarioRepository
import co.com.ceiba.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.Future
import scala.util.{Failure, Success}

class EliminarUsuarioServiceTest extends AsyncWordSpec with MustMatchers with MockitoSugar {

  "EliminarUsuarioService" should {

    "Eliminar correctamente" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val eliminarUsuarioService = new EliminarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(true)

      when(repositorioMock.delete(usuario.id)) thenReturn Future(usuario.id)

      eliminarUsuarioService
        .eliminar(usuario.id) must equal(Success(usuario))
    }

    "Fallar si el usuario no existe" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val eliminarUsuarioService = new EliminarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(false)

      eliminarUsuarioService
        .eliminar(usuario.id) must equal(Failure(NoExisteException()))
    }

  }
}
