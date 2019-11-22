package co.com.ceiba.aplication.services

import co.com.ceiba.domain.usuario.UsuarioRepository
import co.com.ceiba.domain.utils.UsuarioTestProvider
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

      consultarUsuariosService.all().map(_ must equal(usuarios))
    }

    "consultar por Id correctamente" in {

      val usuario = UsuarioTestProvider.unUsuario()

      val repositorioMock = mock[UsuarioRepository]

      val consultarUsuariosService = new ConsultarUsuarioService(repositorioMock)

      when(repositorioMock.getById(usuario.id)) thenReturn Future(usuario)

      consultarUsuariosService.byId(usuario.id).map(_ must equal(usuario))

    }

  }
}
