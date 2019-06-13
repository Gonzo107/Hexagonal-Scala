package co.com.ceiba.services

import co.com.ceiba.exceptions.NoExisteException
import co.com.ceiba.usuario.UsuarioRepository
import co.com.ceiba.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

import scala.util.{Failure, Success}

class EliminarUsuarioServiceTest extends PlaySpec with MockitoSugar {

  "EliminarUsuarioService" should {

    "Eliminar correctamente" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val eliminarUsuarioService = new EliminarUsuarioService(repositorioMock)


      when(repositorioMock.getById(usuario.id)) thenReturn Some(usuario)

      when(repositorioMock.delete(usuario.id)) thenReturn Success(usuario)

      eliminarUsuarioService
        .eliminar(usuario.id) must equal(Success(usuario))
    }

    "Fallar si el usuario no existe" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val eliminarUsuarioService = new EliminarUsuarioService(repositorioMock)


      when(repositorioMock.getById(usuario.id)) thenReturn None

      eliminarUsuarioService
        .eliminar(usuario.id) must equal(Failure(NoExisteException()))
    }

  }
}
