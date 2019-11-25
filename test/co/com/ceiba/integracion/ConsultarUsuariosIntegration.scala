package co.com.ceiba.integracion

import co.com.ceiba.domain.user.{User, UserRepository}
import co.com.ceiba.domain.utils.UsuarioTestProvider
import co.com.ceiba.infrastructure.driver.api_rest.formats.UsuarioFormat
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.http.Status
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient


class ConsultarUsuariosIntegration extends PlaySpec with GuiceOneServerPerSuite with ScalaFutures with IntegrationPatience with UsuarioFormat {


  "Aplicacion" should {
    "Responder vacio al llamar el endpoint /usuarios con GET cuando no hay usuarios" in {
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])
      val peticion = wsUrl("/usuarios").get

      peticion.futureValue.status must equal(Status.OK)

      val respuesta = peticion.futureValue.body[JsValue].as[Seq[User]]

      respuesta must equal(Seq())

    }

    "Responder 404 al llamar el endpoint /usuarios/:Id con GET cuando no hay usuarios" in {
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])
      val peticion = wsUrl(s"/usuarios/${123456}").get

      peticion.futureValue.status must equal(Status.NOT_FOUND)

    }


    "Responder la lista de usuarios al llamar el endpoint /usuarios con GET cuando hay usuarios" in {

      val usuarios = app.injector.instanceOf(classOf[UserRepository])
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])

      val usuarioTest = UsuarioTestProvider.unUsuario()

      usuarios.save(usuarioTest)

      val peticion = wsUrl("/usuarios").get

      peticion.futureValue.status must equal(Status.OK)

      val respuesta = peticion.futureValue.body[JsValue].as[Seq[User]]

      respuesta must equal(Seq(usuarioTest))

    }

    "Responder el usuario al llamar el endpoint /usuarios/:id con GET cuando hay usuarios" in {

      val usuarios = app.injector.instanceOf(classOf[UserRepository])
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])

      val usuarioTest = UsuarioTestProvider.unUsuario()

      usuarios.save(usuarioTest)

      val peticion = wsUrl(s"/usuarios/${usuarioTest.id}").get

      peticion.futureValue.status must equal(Status.OK)

      val respuesta = peticion.futureValue.body[JsValue].as[User]

      respuesta must equal(usuarioTest)

    }
  }

}
