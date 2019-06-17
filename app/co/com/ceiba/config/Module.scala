package co.com.ceiba.config

import co.com.ceiba.commands.CommandHandler
import co.com.ceiba.services._
import co.com.ceiba.usuario.{UsuarioRepository, UsuarioRepositoryAdapter}
import com.google.inject.AbstractModule


class Module extends AbstractModule {


  override def configure(): Unit = {
    bind(classOf[UsuarioRepository]).to(classOf[UsuarioRepositoryAdapter])

    bind(classOf[ConsultarUsuariosUseCase]).to(classOf[ConsultarUsuarioService])
    bind(classOf[EliminarUsuarioUseCase]).to(classOf[EliminarUsuarioService])
    bind(classOf[RegistrarUsuarioUseCase]).to(classOf[RegistrarUsuarioService])

    bind(classOf[CommandHandler]).to(classOf[CommandHandler])



  }
}