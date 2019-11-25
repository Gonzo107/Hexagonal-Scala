package co.com.ceiba.infrastructure.driver.api_rest.controllers

import cats.implicits._
import co.com.ceiba.domain.exception.DomainError.NotPerformed
import co.com.ceiba.domain.services.UserService
import co.com.ceiba.infrastructure.driver.api_rest.formats.UsuarioFormat
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class UsuariosController @Inject()(cc: ControllerComponents,
                                   userService: UserService)
                                  (implicit ec: ExecutionContext)
  extends AbstractController(cc) with UsuarioFormat {


  def getAll: Action[AnyContent] = Action.async {

    userService.all()
      .fold({
        case NotPerformed() => InternalServerError
      },
        users => Ok(Json.toJson(users)))

  }


}
