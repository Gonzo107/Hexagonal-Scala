package co.com.ceiba.port.driver.api_rest.controllers

import cats.implicits._
import co.com.ceiba.domain.exception.DomainError.{AlreadyExists, NotExists, NotPerformed}
import co.com.ceiba.domain.services.UserService
import co.com.ceiba.domain.user.User.IdUser
import co.com.ceiba.domain.user.UserSeed
import co.com.ceiba.port.driver.api_rest.formats.UserFormats
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class UsersController @Inject()(cc: ControllerComponents,
                                userService: UserService)
                               (implicit ec: ExecutionContext)
  extends AbstractController(cc) with UserFormats {


  def getAll: Action[AnyContent] = Action.async {

    userService.all()
      .fold({
        case NotPerformed() => InternalServerError
      },
        users => Ok(Json.toJson(users)))

  }

  def getById(id: IdUser): Action[AnyContent] = Action.async {
    userService.byId(id)
      .fold(
        {
          case NotPerformed() => InternalServerError
          case NotExists() => NotFound
        },
        user => Ok(Json.toJson(user))
      )
  }

  def create(): Action[UserSeed] = Action(parse.json[UserSeed]).async {
    request =>
      userService.create(request.body).fold(
        {
          case NotPerformed() => InternalServerError
          case AlreadyExists() => Conflict
        },
        userCreated => Ok(Json.toJson(userCreated))
      )

  }

  def delete(id: IdUser) = Action.async {
    userService.delete(id).fold(
      {
        case NotPerformed() => InternalServerError
        case NotExists() => NotFound

      },
      deletedId => Ok(deletedId)
    )
  }


}
