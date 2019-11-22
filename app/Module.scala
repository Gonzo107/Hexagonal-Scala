import co.com.ceiba.aplication.services.UserServiceImpl
import co.com.ceiba.domain.services.UserService
import co.com.ceiba.domain.usuario.UsuarioRepository
import co.com.ceiba.infraestructura.driven.persistencia_h2.usuario.UsuarioRepositoryAdapter
import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}


class Module(environment: Environment, configuration: Configuration)
  extends AbstractModule {


  override def configure(): Unit = {
    bind(classOf[UsuarioRepository]).to(classOf[UsuarioRepositoryAdapter])

    bind(classOf[UserService]).to(classOf[UserServiceImpl])

  }
}