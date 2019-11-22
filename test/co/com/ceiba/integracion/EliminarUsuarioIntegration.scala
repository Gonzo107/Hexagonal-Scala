package co.com.ceiba.integracion

import co.com.ceiba.aplication.services.EliminarUsuarioService
import co.com.ceiba.domain.usuario.{Usuario, UsuarioRepository}
import co.com.ceiba.domain.utils.UsuarioTestProvider
import co.com.ceiba.infraestructura.driver.api_rest.formats.UsuarioFormat
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.http.Status
import play.api.libs.ws.WSClient

class EliminarUsuarioIntegration extends PlaySpec
  with GuiceOneServerPerSuite
  with ScalaFutures
  with IntegrationPatience
  with UsuarioFormat
  with CommandFormats
  with BeforeAndAfter {

  val usuario: Usuario = UsuarioTestProvider.unUsuario()

  before {
    val usuarios: UsuarioRepository = app.injector.instanceOf(classOf[UsuarioRepository])

    if (usuarios.exists(usuario.id).futureValue) {
      usuarios.delete(usuario.id)
    }
  }

  after {
    val usuarios: UsuarioRepository = app.injector.instanceOf(classOf[UsuarioRepository])

    if (usuarios.exists(usuario.id).futureValue) {
      usuarios.delete(usuario.id)
    }
  }

  "Aplicacion" should {

    "Responder error enviar un id de usuario inexistente a /usuarios/:Id por DELETE" in {
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])

      val usuario = UsuarioTestProvider.unUsuario()

      val peticion = wsUrl(s"/usuarios/${usuario.id}").delete()

      peticion.futureValue.status must equal(Status.BAD_REQUEST)

      val respuesta = peticion.futureValue.body[String]

      respuesta must equal(EliminarUsuarioService.USUARIO_NO_EXISTENTE)

    }

    "Responder el id del usuario eliminado al enviar un id de usuario existente a /usuarios/:Id por DELETE " in {
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])
      val usuarios: UsuarioRepository = app.injector.instanceOf(classOf[UsuarioRepository])

      val usuario = UsuarioTestProvider.unUsuario()

      usuarios.save(usuario)

      val peticion = wsUrl(s"/usuarios/${usuario.id}").delete()

      peticion.futureValue.status must equal(Status.OK)

      val respuesta = peticion.futureValue.body

      respuesta must equal(s""""${usuario.id}"""")

    }
  }

}
