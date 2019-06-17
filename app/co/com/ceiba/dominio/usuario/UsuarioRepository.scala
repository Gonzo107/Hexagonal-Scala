package co.com.ceiba.dominio.usuario


import co.com.ceiba.dominio.usuario.Usuario.IdUsuario

import scala.concurrent.Future

trait UsuarioRepository {

  def save(usuario: Usuario): Future[Usuario]

  def getAll(): Future[Seq[Usuario]]

  def getById(id: Usuario.IdUsuario): Future[Usuario]

  def exists(id: Usuario.IdUsuario): Future[Boolean]

  def delete(id: Usuario.IdUsuario): Future[IdUsuario]

}
