package co.com.ceiba.services

import co.com.ceiba.exceptions.YaExisteException
import co.com.ceiba.usuario.UsuarioRepository
import co.com.ceiba.utils.UsuarioTestProvider
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.Future
import scala.util.{Failure, Success}

class RegistarUsuarioServiceTest extends AsyncWordSpec with MustMatchers with MockitoSugar {


  "RegistarUsuarioService" should {

    "Registrar correctamente" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val registarUsuarioService = new RegistarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(false)

      when(repositorioMock.save(usuario)) thenReturn Future(usuario)

      registarUsuarioService
        .registrar(
          usuario.id,
          usuario.nombre,
          usuario.apellido,
          usuario.email) must equal(Success(usuario))
    }

    "Fallar si el usuario ya existe" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val registarUsuarioService = new RegistarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(false)

      registarUsuarioService
        .registrar(
          usuario.id,
          usuario.nombre,
          usuario.apellido,
          usuario.email) must equal(Failure(YaExisteException()))
    }

  }
}
