package co.com.ceiba.driver.api_rest.controllers


import cats.implicits._
import co.com.ceiba.driver.api_rest.formats.UserFormats
import co.com.ceiba.exception.DomainError.{AlreadyExists, NotExists, NotPerformed}
import co.com.ceiba.services.UserService
import co.com.ceiba.user.User.IdUser
import co.com.ceiba.user.UserSeed
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class UsersController @Inject()(cc: ControllerComponents,
                                userService: UserService)
                               (implicit ec: ExecutionContext)
  extends AbstractController(cc) with UserFormats {


  def getAll: Action[AnyContent] = Action.async {

    userService.all()
      .fold({
        case _ => InternalServerError
      },
        users => Ok(Json.toJson(users)))

  }

  def getById(id: IdUser): Action[AnyContent] = Action.async {
    userService.byId(id)
      .fold(
        {
          case NotExists() => NotFound
          case _ => InternalServerError
        },
        user => Ok(Json.toJson(user))
      )
  }

  def create(): Action[UserSeed] = Action(parse.json[UserSeed]).async {
    request =>
      userService.create(request.body).fold(
        {
          case AlreadyExists() => Conflict
          case _ => InternalServerError
        },
        userCreated => Ok(Json.toJson(userCreated))
      )

  }

  def delete(id: IdUser) = Action.async {
    userService.delete(id).fold(
      {

        case NotExists() => NotFound
        case _ => InternalServerError

      },
      deletedId => Ok(deletedId)
    )
  }


}
