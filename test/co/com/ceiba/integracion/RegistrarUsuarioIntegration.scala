package co.com.ceiba.integracion

import co.com.ceiba.aplication.commands.RegistrarUsuarioCommand
import co.com.ceiba.aplication.services.RegistrarUsuarioService
import co.com.ceiba.domain.usuario.{Usuario, UsuarioRepository}
import co.com.ceiba.domain.utils.UsuarioTestProvider
import co.com.ceiba.infraestructura.driver.api_rest.formats.UsuarioFormat
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.http.Status
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson
import play.api.libs.ws.WSClient

class RegistrarUsuarioIntegration extends PlaySpec
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
    "Responder el usuario creado al enviar un RegistrarUsuarioCommand a /usuarios por post" in {
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])
      val usuarios: UsuarioRepository = app.injector.instanceOf(classOf[UsuarioRepository])

      val usuario = UsuarioTestProvider.unUsuario()

      val body = RegistrarUsuarioCommand(usuario.id, usuario.nombre, usuario.apellido, usuario.email)
      val peticion = wsUrl("/usuarios").post(toJson(body))

      peticion.futureValue.status must equal(Status.CREATED)

      val respuesta = peticion.futureValue.body[JsValue].as[Usuario]

      respuesta must equal(usuario)

    }

    "Responder el error al enviar un RegistrarUsuarioCommand a /usuarios por post y el usuario ya existe" in {


      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])
      val usuarios: UsuarioRepository = app.injector.instanceOf(classOf[UsuarioRepository])

      val usuario = UsuarioTestProvider.unUsuario()

      usuarios.save(usuario)

      val body = RegistrarUsuarioCommand(usuario.id, usuario.nombre, usuario.apellido, usuario.apellido)

      val peticion = wsUrl("/usuarios").post(toJson(body))
      peticion.futureValue.status must equal(Status.BAD_REQUEST)

      val respuesta = peticion.futureValue.body[String]

      respuesta must equal(RegistrarUsuarioService.USUARIO_YA_EXISTE)

    }
  }

}

