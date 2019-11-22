package co.com.ceiba.domain.services

import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.usuario.Usuario

trait UserService {

  def delete(id: Usuario.IdUsuario): Result[Usuario.IdUsuario]

  def all(): Result[Seq[Usuario]]

  def byId(id: Usuario.IdUsuario): Result[Usuario]

  def create(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Result[Usuario]

}
