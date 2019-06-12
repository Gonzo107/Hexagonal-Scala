package co.com.ceiba.services

import co.com.ceiba.factory.UsuarioFactory
import co.com.ceiba.usuario.{Usuario, UsuarioRepository}

import scala.util.Try

class RegistarUsuarioService(usuarios: UsuarioRepository) extends RegistrarUsuarioUseCase {

  override def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Try[Usuario] = {

    val usuarioEntrante = UsuarioFactory.crear(id, nombre, apellido, email)

    usuarios.save(usuarioEntrante)
  }
}
