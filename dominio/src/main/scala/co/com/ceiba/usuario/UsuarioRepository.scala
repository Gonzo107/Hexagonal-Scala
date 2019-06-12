package co.com.ceiba.usuario

import scala.util.Try

trait UsuarioRepository {

  def save(usuario: Usuario): Try[Usuario]

  def getAll(): Seq[Usuario]

  def getById(id: Usuario.IdUsuario): Option[Usuario]

  def delete(id: Usuario.IdUsuario): Try[Usuario]
}
