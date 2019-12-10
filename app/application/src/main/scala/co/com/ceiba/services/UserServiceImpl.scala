package co.com.ceiba.services


import cats.data.EitherT
import cats.implicits._
import co.com.ceiba.exception.DomainError
import co.com.ceiba.execution.ExecutionDomain.Result
import co.com.ceiba.factory.UserFactory
import co.com.ceiba.user.User.IdUser
import co.com.ceiba.user.{User, UserRepository, UserSeed}
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl @Inject()(users: UserRepository)(implicit ec: ExecutionContext) extends UserService {


  override def delete(id: IdUser): Result[IdUser] = {
    for {
      exists <- users.exists(id)
      deleted <- if (exists) {
        users.delete(id)
      } else {
        EitherT.leftT[Future, User.IdUser](DomainError.notExists())
      }
    } yield deleted

  }

  override def all(): Result[Seq[User]] = users.getAll()

  override def byId(id: IdUser): Result[User] = users.getById(id)

  override def create(seed: UserSeed): Result[User] = {
    for {
      exists <- users.exists(seed.id)
      deleted <- if (!exists) {
        val user = UserFactory.from(seed)
        users.save(user)
      } else {
        EitherT.leftT[Future, User](DomainError.exists())
      }
    } yield deleted

  }
}
