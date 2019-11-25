package co.com.ceiba.aplication.services

import co.com.ceiba.domain.user.UserRepository
import co.com.ceiba.domain.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.Future

class RegistrarUserServiceTest extends AsyncWordSpec with MustMatchers with MockitoSugar {


  "RegistarUsuarioService" should {

    "Registrar correctamente" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UserRepository]

      val registarUsuarioService = new RegistrarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(false)

      when(repositorioMock.save(usuario)) thenReturn Future(usuario)

      registarUsuarioService
        .registrar(
          usuario.id,
          usuario.name,
          usuario.surname,
          usuario.email).map(_ must equal(usuario))
    }

    "Fallar si el usuario ya existe" in {
      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UserRepository]

      val registarUsuarioService = new RegistrarUsuarioService(repositorioMock)


      when(repositorioMock.exists(usuario.id)) thenReturn Future(true)

      registarUsuarioService
        .registrar(
          usuario.id,
          usuario.name,
          usuario.surname,
          usuario.email).map(_ => fail())
        .recover({
          case AlreadyExists(e) => e must equal("Ya existe un usuario con el id ingresado")
        })
    }

  }
}
