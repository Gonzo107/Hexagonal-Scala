import co.com.ceiba.aplicacion.commands.CommandHandler
import co.com.ceiba.aplicacion.services.{ConsultarUsuarioService, EliminarUsuarioService, RegistrarUsuarioService}
import co.com.ceiba.dominio.services.{ConsultarUsuariosUseCase, EliminarUsuarioUseCase, RegistrarUsuarioUseCase}
import co.com.ceiba.dominio.usuario.UsuarioRepository
import co.com.ceiba.infraestructura.driven.persistencia_h2.usuario.UsuarioRepositoryAdapter
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import play.api.{Configuration, Environment}


class Module(environment: Environment, configuration: Configuration)
  extends AbstractModule
    with ScalaModule {


  override def configure(): Unit = {
    bind(classOf[UsuarioRepository]).to(classOf[UsuarioRepositoryAdapter])

    bind(classOf[ConsultarUsuariosUseCase]).to(classOf[ConsultarUsuarioService])
    bind(classOf[EliminarUsuarioUseCase]).to(classOf[EliminarUsuarioService])
    bind(classOf[RegistrarUsuarioUseCase]).to(classOf[RegistrarUsuarioService])

  }
}