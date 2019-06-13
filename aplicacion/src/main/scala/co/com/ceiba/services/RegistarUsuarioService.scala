package co.com.ceiba.services

import co.com.ceiba.exceptions.YaExisteException
import co.com.ceiba.factory.UsuarioFactory
import co.com.ceiba.usuario.{Usuario, UsuarioRepository}

import scala.util.{Failure, Try}

class RegistarUsuarioService(usuarios: UsuarioRepository) extends RegistrarUsuarioUseCase {

  override def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Try[Usuario] = {

    usuarios.getById(id) match {
      case Some(u) => Failure(YaExisteException())
      case None => usuarios.save(UsuarioFactory.crear(id, nombre, apellido, email))
    }
  }
}
