package co.com.ceiba.aplicacion.services

import co.com.ceiba.dominio.usuario.UsuarioRepository
import co.com.ceiba.dominio.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, MustMatchers}

import scala.concurrent.Future

class ConsultarUsuarioServiceTest extends AsyncWordSpec with MustMatchers with MockitoSugar {

  "ConsultarUsuarioService" should {

    "consultar todos correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      val usuarios = Seq(usuario, usuario, usuario)

      val repositorioMock = mock[UsuarioRepository]

      val consultarUsuariosService = new ConsultarUsuarioService(repositorioMock)

      when(repositorioMock.getAll()) thenReturn Future(usuarios)

      consultarUsuariosService.consultarTodos() must equal(usuarios)
    }

    "consultar por Id correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val consultarUsuariosService = new ConsultarUsuarioService(repositorioMock)

      when(repositorioMock.getById(usuario.id)) thenReturn Future(usuario)

      consultarUsuariosService.consultarPorId(usuario.id) must equal(Some(usuario))

    }

  }
}