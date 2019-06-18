package co.com.ceiba.aplicacion.services

import co.com.ceiba.dominio.exceptions.NoExisteException
import co.com.ceiba.dominio.usuario.UsuarioRepository
import co.com.ceiba.dominio.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.Future

class EliminarUsuarioServiceTest extends AsyncWordSpec with MustMatchers with MockitoSugar {

  "EliminarUsuarioService" should {

    "Eliminar correctamente" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val eliminarUsuarioService = new EliminarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(true)

      when(repositorioMock.delete(usuario.id)) thenReturn Future(usuario.id)

      eliminarUsuarioService
        .eliminar(usuario.id).map(idCreado => idCreado must equal(usuario.id))
    }

    "Fallar si el usuario no existe" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val eliminarUsuarioService = new EliminarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(false)

      eliminarUsuarioService
        .eliminar(usuario.id).map(_=>fail).recover({ case NoExisteException(e) => e must equal("No se puede eliminar un usuario no existente") })
    }

  }
}
