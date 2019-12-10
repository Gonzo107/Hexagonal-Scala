package co.com.ceiba.aplication.services

import cats.data.EitherT
import cats.implicits._
import co.com.ceiba.domain.utils.UserTestProvider
import co.com.ceiba.exception.DomainError.AlreadyExists
import co.com.ceiba.execution.ExecutionDomain.Result
import co.com.ceiba.services.UserServiceImpl
import co.com.ceiba.user.{User, UserRepository}
import org.scalatest.{AsyncWordSpec, BeforeAndAfter, MustMatchers}
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito.{reset, when}

import scala.concurrent.Future

class UserServiceTest extends AsyncWordSpec with MustMatchers with MockitoSugar with BeforeAndAfter {

  val userRepository: UserRepository = mock[UserRepository]
  val usersService = new UserServiceImpl(userRepository)

  before {
    reset(userRepository)
  }

  "UserService" can {
    "Query all users" when {
      "there are users stored" should {
        "produce an set withe the existing users" in {
          val user = UserTestProvider.aUser()

          val expectedUsers = Seq(user, user, user)

          val storedUsers: Result[Seq[User]] = EitherT.fromEither[Future](Right(expectedUsers))

          when(userRepository.getAll()).thenReturn(storedUsers)

          usersService.all().fold(
            (_ => fail),
            obtainedUsers => obtainedUsers mustBe (expectedUsers)
          )
        }
      }
      "there are no users stored" should {
        "produce an empty set of users" in {
          val storedUsers: Result[Seq[User]] = EitherT.fromEither[Future](Right(Seq()))

          when(userRepository.getAll()).thenReturn(storedUsers)

          usersService.all().fold(
            (_ => fail),
            obtainedUsers => obtainedUsers mustBe (Seq())
          )
        }
      }
    }

    "Query a user by it's id" when {
      "the user does exists" should {
        "produce the user" in {
          val user = UserTestProvider.aUser()

          val userExists: Result[Boolean] = EitherT.fromEither[Future](Right(true))
          val storedUser: Result[User] = EitherT.fromEither[Future](Right(user))

          when(userRepository.exists(user.id)) thenReturn (userExists)
          when(userRepository.getById(user.id)).thenReturn(storedUser)

          usersService.byId(user.id).fold(
            (_ => fail),
            obtainedUser => obtainedUser mustBe (user)
          )
        }
      }
    }

    "Create a user given a seed" when {
      "the user does not exists" should {
        "create the user and produce it" in {
          val seed = UserTestProvider.aSeed()
          val user = UserTestProvider.aUser()

          val userExists: Result[Boolean] = EitherT.fromEither[Future](Right(false))
          val storedUser: Result[User] = EitherT.fromEither[Future](Right(user))

          when(userRepository.exists(user.id)) thenReturn (userExists)
          when(userRepository.save(user)).thenReturn(storedUser)

          usersService.create(seed).fold(
            (_ => fail),
            createdUser => createdUser mustBe (user)
          )
        }
      }
      "the user exists" should {
        "communicate it already exists" in {
          val seed = UserTestProvider.aSeed()
          val user = UserTestProvider.aUser()

          val userExists: Result[Boolean] = EitherT.fromEither[Future](Right(true))

          when(userRepository.exists(user.id)) thenReturn (userExists)

          usersService.create(seed).fold(
            {
              case AlreadyExists() => succeed
              case _ => fail
            },
            _ => fail
          )
        }
      }
    }

    "Delete a user by it's id" when {
      "the user does exists" should {
        "delete the user and produce the deleted id" in {
          val usuario = UserTestProvider.aUser()

          val usuarioExiste: Result[Boolean] = EitherT.fromEither[Future](Right(true))
          val usuarioAlmacenado: Result[User.IdUser] = EitherT.fromEither[Future](Right(usuario.id))

          when(userRepository.exists(usuario.id)) thenReturn (usuarioExiste)
          when(userRepository.delete(usuario.id)).thenReturn(usuarioAlmacenado)

          usersService.delete(usuario.id).fold(
            (_ => fail),
            deletedId => deletedId mustBe (usuario.id)
          )
        }
      }
    }

  }

}
