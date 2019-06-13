package co.com.ceiba.services

import co.com.ceiba.usuario.UsuarioRepository
import co.com.ceiba.utils.UsuarioTestProvider
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

class ConsultarUsuarioServiceTest extends PlaySpec with MockitoSugar {

  "ConsultarUsuarioService" should {

    "consultar todos correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      val usuarios = Seq(usuario, usuario, usuario)

      val repositorioMock = mock[UsuarioRepository]

      val consultarUsuariosService = new ConsultarUsuarioService(repositorioMock)

      when(repositorioMock.getAll()) thenReturn usuarios

      consultarUsuariosService.consultarTodos() must equal(usuarios)
    }

    "consultar por Id correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val consultarUsuariosService = new ConsultarUsuarioService(repositorioMock)

      when(repositorioMock.getById(usuario.id)) thenReturn Some(usuario)

      consultarUsuariosService.consultarPorId(usuario.id) must equal(Some(usuario))

    }

  }
}
