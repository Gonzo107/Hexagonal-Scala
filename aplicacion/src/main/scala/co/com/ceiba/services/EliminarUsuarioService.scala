package co.com.ceiba.services

import co.com.ceiba.usuario.Usuario.IdUsuario
import co.com.ceiba.usuario.{Usuario, UsuarioRepository}

import scala.util.Try

class EliminarUsuarioService(usuarios: UsuarioRepository) extends EliminarUsuarioUseCase {

  override def eliminar(id: IdUsuario): Try[Usuario] = {
    usuarios.delete(id)
  }

}