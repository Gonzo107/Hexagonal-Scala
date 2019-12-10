package co.com.ceiba.services

import co.com.ceiba.execution.ExecutionDomain.Result
import co.com.ceiba.user.{User, UserSeed}

trait UserService {

  def delete(id: User.IdUser): Result[User.IdUser]

  def all(): Result[Seq[User]]

  def byId(id: User.IdUser): Result[User]

  def create(seed: UserSeed): Result[User]

}
