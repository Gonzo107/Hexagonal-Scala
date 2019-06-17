package co.com.ceiba.dominio.services

import co.com.ceiba.dominio.usuario.Usuario

import scala.concurrent.Future

trait EliminarUsuarioUseCase {

  def eliminar(id: Usuario.IdUsuario): Future[Usuario.IdUsuario]

}
