package co.com.ceiba.aplication.services

import cats.data.EitherT
import cats.implicits._
import co.com.ceiba.aplication.factory.UserFactory
import co.com.ceiba.domain.exception.DomainError
import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.services.UserService
import co.com.ceiba.domain.user.User.IdUser
import co.com.ceiba.domain.user.{User, UserRepository, UserSeed}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl @Inject()(usuarios: UserRepository)(implicit ec: ExecutionContext) extends UserService {


  override def delete(id: IdUser): Result[IdUser] = {
    for {
      exists <- usuarios.exists(id)
      deleted <- if (exists) {
        usuarios.delete(id)
      } else {
        EitherT.leftT[Future, User.IdUser](DomainError.notExists())
      }
    } yield deleted

  }

  override def all(): Result[Seq[User]] = usuarios.getAll()

  override def byId(id: IdUser): Result[User] = usuarios.getById(id)

  override def create(seed: UserSeed): Result[User] = {
    for {
      exists <- usuarios.exists(seed.id)
      deleted <- if (!exists) {
        val user = UserFactory.from(seed)
        usuarios.save(user)
      } else {
        EitherT.leftT[Future, User](DomainError.exists())
      }
    } yield deleted

  }
}
