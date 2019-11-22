package co.com.ceiba.domain.usuario


import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.usuario.Usuario.IdUsuario

import scala.concurrent.Future

trait UsuarioRepository {

  def save(usuario: Usuario): Result[Usuario]

  def getAll(): Result[Seq[Usuario]]

  def getById(id: Usuario.IdUsuario): Result[Usuario]

  def exists(id: Usuario.IdUsuario): Result[Boolean]

  def delete(id: Usuario.IdUsuario): Result[IdUsuario]

}
