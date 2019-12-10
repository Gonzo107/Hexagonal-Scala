package co.com.ceiba.user

import cats.data.EitherT
import cats.implicits._
import co.com.ceiba.domain.utils.UserTestProvider
import co.com.ceiba.driver.api_rest.formats.UserFormats
import co.com.ceiba.execution.ExecutionDomain.Result
import org.mockito.Mockito.when
import org.scalatest.{AsyncTestSuite, MustMatchers}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json._
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class restApiTest extends PlaySpec with MustMatchers with MockitoSugar with GuiceOneAppPerSuite with AsyncTestSuite with UserFormats {

  val userRepository: UserRepository = mock[UserRepository]

  override def fakeApplication(): Application = {
    GuiceApplicationBuilder()
      .overrides(bind[UserRepository].to(userRepository))
      .build()
  }

  "Users REST api" must {
    "expose CRUD operations for Users" must {
      "expose Read operations" must {
        "expose all users" when {
          "a GET request is made to /users" in {
            val request = FakeRequest("GET", "/users")
            val Some(result) = route(app, request)

            val user = UserTestProvider.aUser()

            val expectedUsers = Seq(user, user, user)

            val storedUsers: Result[Seq[User]] = EitherT.fromEither[Future](Right(expectedUsers))

            when(userRepository.getAll()).thenReturn(storedUsers)

            val obtainedUsers = contentAsJson(result)

            status(result) mustEqual OK

            obtainedUsers mustEqual (Json.toJson(expectedUsers))
          }
        }
        "expose a user given it's Id" when {
          "a GET request is made to /users/:id" when {
            ":id is the user's Id" in {

              val user = UserTestProvider.aUser()

              val request = FakeRequest("GET", s"/users/${user.id}")

              val Some(result) = route(app, request)

              val storedUsers: Result[User] = EitherT.fromEither[Future](Right(user))

              when(userRepository.getById(user.id)).thenReturn(storedUsers)

              val obtainedUsers = contentAsJson(result)

              status(result) mustEqual OK

              obtainedUsers mustEqual (Json.toJson(user))
            }
          }
        }
      }
    }
  }


}
