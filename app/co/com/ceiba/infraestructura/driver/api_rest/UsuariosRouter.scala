package co.com.ceiba.infraestructura.driver.api_rest

import co.com.ceiba.infraestructura.driver.api_rest.controllers.UsuariosController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._


class UsuariosRouter @Inject()(controller: UsuariosController) extends SimpleRouter {


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