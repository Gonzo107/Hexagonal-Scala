package co.com.ceiba.aplicacion.services

import co.com.ceiba.dominio.exceptions.YaExisteException
import co.com.ceiba.dominio.usuario.UsuarioRepository
import co.com.ceiba.dominio.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.Future

class RegistrarUsuarioServiceTest extends AsyncWordSpec with MustMatchers with MockitoSugar {


  "RegistarUsuarioService" should {

    "Registrar correctamente" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val registarUsuarioService = new RegistrarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(false)

      when(repositorioMock.save(usuario)) thenReturn Future(usuario)

      registarUsuarioService
        .registrar(
          usuario.id,
          usuario.nombre,
          usuario.apellido,
          usuario.email).map(_ must equal(usuario))
    }

    "Fallar si el usuario ya existe" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val registarUsuarioService = new RegistrarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(true)

      registarUsuarioService
        .registrar(
          usuario.id,
          usuario.nombre,
          usuario.apellido,
          usuario.email).map(_ => fail())
        .recover({
          case YaExisteException(e) => e must equal("Ya existe un usuario con el id ingresado")
        })
    }

  }
}
