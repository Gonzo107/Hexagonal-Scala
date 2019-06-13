package co.com.ceiba.services

import co.com.ceiba.exceptions.NoExisteException
import co.com.ceiba.usuario.Usuario.IdUsuario
import co.com.ceiba.usuario.{Usuario, UsuarioRepository}

import scala.util.{Failure, Try}

class EliminarUsuarioService(usuarios: UsuarioRepository) extends EliminarUsuarioUseCase {

  override def eliminar(id: IdUsuario): Try[Usuario] = {

    usuarios.getById(id) match {
      case Some(u) => usuarios.delete(id)
      case None => Failure(NoExisteException())
    }

  }

}