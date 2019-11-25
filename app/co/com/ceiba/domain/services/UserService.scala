package co.com.ceiba.domain.services

import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.user.User

trait UserService {

  def delete(id: User.IdUser): Result[User.IdUser]

  def all(): Result[Seq[User]]

  def byId(id: User.IdUser): Result[User]

  def create(id: User.IdUser, nombre: String, apellido: String, email: String): Result[User]

}
