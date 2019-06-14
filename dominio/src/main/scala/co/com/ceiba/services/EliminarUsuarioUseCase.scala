package co.com.ceiba.services

import co.com.ceiba.usuario.Usuario

import scala.concurrent.Future

trait EliminarUsuarioUseCase {

  def eliminar(id: Usuario.IdUsuario): Future[Usuario.IdUsuario]

}
