package co.com.ceiba.config

import co.com.ceiba.commands.CommandHandler
import co.com.ceiba.services.{ConsultarUsuarioService, ConsultarUsuariosUseCase}
import co.com.ceiba.usuario.{UsuarioRepository, UsuarioRepositoryAdapter}
import com.google.inject.AbstractModule


class Module extends AbstractModule{



  override def configure(): Unit = {
    bind(classOf[UsuarioRepository]).to(classOf[UsuarioRepositoryAdapter])
    bind(classOf[ConsultarUsuariosUseCase]).toInstance()
    bind(classOf[CommandHandler]).toInstance()

    bind(classOf[UserRegister])
      .toInstance(userRegister)
  }

}