import co.com.ceiba.aplication.services.UserServiceImpl
import co.com.ceiba.domain.services.UserService
import co.com.ceiba.domain.user.UserRepository
import co.com.ceiba.infrastructure.driven.h2_persistence.user.UserRepositoryAdapter
import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}


class Module(environment: Environment, configuration: Configuration)
  extends AbstractModule {


  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryAdapter])

    bind(classOf[UserService]).to(classOf[UserServiceImpl])

  }
}