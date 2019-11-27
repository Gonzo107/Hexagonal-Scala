package co.com.ceiba.integracion

import co.com.ceiba.aplication.commands.RegistrarUsuarioCommand
import co.com.ceiba.aplication.services.RegistrarUsuarioService
import co.com.ceiba.domain.user.{User, UserRepository}
import co.com.ceiba.domain.utils.UsuarioTestProvider
import co.com.ceiba.port.driver.api_rest.formats.UserFormats
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.http.Status
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson
import play.api.libs.ws.WSClient

class RegistrarUserIntegration extends PlaySpec
  with GuiceOneServerPerSuite
  with ScalaFutures
  with IntegrationPatience
  with UserFormats
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
    "Responder el usuario creado al enviar un RegistrarUsuarioCommand a /usuarios por post" in {
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])
      val usuarios: UserRepository = app.injector.instanceOf(classOf[UserRepository])

      val usuario = UsuarioTestProvider.unUsuario()

      val body = RegistrarUsuarioCommand(usuario.id, usuario.name, usuario.surname, usuario.email)
      val peticion = wsUrl("/usuarios").post(toJson(body))

      peticion.futureValue.status must equal(Status.CREATED)

      val respuesta = peticion.futureValue.body[JsValue].as[User]

      respuesta must equal(usuario)

    }

    "Responder el error al enviar un RegistrarUsuarioCommand a /usuarios por post y el usuario ya existe" in {


      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])
      val usuarios: UserRepository = app.injector.instanceOf(classOf[UserRepository])

      val usuario = UsuarioTestProvider.unUsuario()

      usuarios.save(usuario)

      val body = RegistrarUsuarioCommand(usuario.id, usuario.name, usuario.surname, usuario.surname)

      val peticion = wsUrl("/usuarios").post(toJson(body))
      peticion.futureValue.status must equal(Status.BAD_REQUEST)

      val respuesta = peticion.futureValue.body[String]

      respuesta must equal(RegistrarUsuarioService.USUARIO_YA_EXISTE)

    }
  }

}

