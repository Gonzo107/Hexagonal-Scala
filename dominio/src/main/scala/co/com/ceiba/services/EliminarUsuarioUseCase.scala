package co.com.ceiba.services

import co.com.ceiba.usuario.Usuario

import scala.util.Try

trait EliminarUsuarioUseCase {

  def eliminar(id: Usuario.IdUsuario): Try[Usuario]

}
