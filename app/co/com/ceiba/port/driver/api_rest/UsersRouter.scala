package co.com.ceiba.port.driver.api_rest

import co.com.ceiba.port.driver.api_rest.controllers.UsersController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._


class UsersRouter @Inject()(controller: UsersController) extends SimpleRouter {


  override def routes: Routes = {
    case GET(p"/") =>
      controller.getAll

    case GET(p"/$id") =>
      controller.getById(id)

    case POST(p"/") =>
      controller.create

    case DELETE(p"/$id") =>
      controller.delete(id)

  }

}