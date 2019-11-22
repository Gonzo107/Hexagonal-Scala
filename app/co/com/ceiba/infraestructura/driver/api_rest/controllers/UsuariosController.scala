package co.com.ceiba.infraestructura.driver.api_rest.controllers

import co.com.ceiba.domain.services.UserService
import co.com.ceiba.infraestructura.driver.api_rest.formats.UsuarioFormat
import javax.inject.Inject
import cats.implicits._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class UsuariosController @Inject()(cc: ControllerComponents,
                                   userService: UserService)
                                  (implicit ec: ExecutionContext)
  extends AbstractController(cc)
    with UsuarioFormat {


  def getAll: Action[AnyContent] = Action.async {
    (for {
      usuarios <- userService.all()

    } yield usuarios)
    Future(Ok("algo"))
  }


}
