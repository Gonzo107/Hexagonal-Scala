import co.com.ceiba.driven.h2_persistence.user.UserRepositoryAdapter
import co.com.ceiba.services.{UserService, UserServiceImpl}
import co.com.ceiba.user.UserRepository
import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}


class Module(environment: Environment, configuration: Configuration)
  extends AbstractModule {


  override def configure(): Unit = {

    bind(classOf[UserRepository]).to(classOf[UserRepositoryAdapter])

    bind(classOf[UserService]).to(classOf[UserServiceImpl])

  }

}