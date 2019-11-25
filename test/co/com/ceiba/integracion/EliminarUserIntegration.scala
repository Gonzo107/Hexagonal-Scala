package co.com.ceiba.integracion

import co.com.ceiba.aplication.services.EliminarUsuarioService
import co.com.ceiba.domain.user.{User, UserRepository}
import co.com.ceiba.domain.utils.UsuarioTestProvider
import co.com.ceiba.infrastructure.driver.api_rest.formats.UsuarioFormat
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.http.Status
import play.api.libs.ws.WSClient

class EliminarUserIntegration extends PlaySpec
  with GuiceOneServerPerSuite
  with ScalaFutures
  with IntegrationPatience
  with UsuarioFormat
  with CommandFormats
  with BeforeAndAfter {

  val usuario: User = UsuarioTestProvider.unUsuario()

  before {
    val usuarios: UserRepository = app.injector.instanceOf(classOf[UserRepository])

    if (usuarios.exists(usuario.id).futureValue) {
      usuarios.delete(usuario.id)
    }
  }

  after {
    val usuarios: UserRepository = app.injector.instanceOf(classOf[UserRepository])

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
      val usuarios: UserRepository = app.injector.instanceOf(classOf[UserRepository])

      val usuario = UsuarioTestProvider.unUsuario()

      usuarios.save(usuario)

      val peticion = wsUrl(s"/usuarios/${usuario.id}").delete()

      peticion.futureValue.status must equal(Status.OK)

      val respuesta = peticion.futureValue.body

      respuesta must equal(s""""${usuario.id}"""")

    }
  }

}
