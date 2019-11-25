package co.com.ceiba.domain.user


import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.user.User.IdUser

trait UserRepository {

  def save(usuario: User): Result[User]

  def getAll(): Result[Seq[User]]

  def getById(id: User.IdUser): Result[User]

  def exists(id: User.IdUser): Result[Boolean]

  def delete(id: User.IdUser): Result[IdUser]

}
