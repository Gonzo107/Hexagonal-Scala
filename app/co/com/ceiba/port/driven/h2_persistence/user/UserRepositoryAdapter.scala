package co.com.ceiba.port.driven.h2_persistence.user


import cats.data.EitherT
import cats.implicits._
import co.com.ceiba.domain.exception.DomainError
import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.user.User.IdUser
import co.com.ceiba.domain.user.{User, UserRepository}
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

class UserRepositoryAdapter @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
(implicit executionContext: ExecutionContext)
  extends UserRepository with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Users = TableQuery[UserTable]

  override def save(user: User): Result[User] = EitherT {
    db.run(Users += user).map({
      case 0 => DomainError.notPerformed().asLeft
      case _ => user.asRight
    })
  }


  override def getAll(): Result[Seq[User]] = safeOperate[Seq[User]] {
    EitherT {
      db.run(Users.result).map(lista => lista.asRight[DomainError])
    }
  }

  override def getById(id: IdUser): Result[User] = safeOperate[User] {
    EitherT {
      db.run(Users.filter(_.id === id).result.headOption).map({
        case None => DomainError.notExists().asLeft
        case Some(head) => head.asRight
      })
    }
  }


  override def delete(id: IdUser): Result[IdUser] = safeOperate[IdUser] {
    EitherT {
      db.run(Users.filter(_.id === id).delete).map({
        case 0 => DomainError.notPerformed().asLeft
        case _ => id.asRight
      })
    }
  }

  override def exists(id: IdUser): Result[Boolean] = safeOperate[Boolean] {
    EitherT {
      db.run(Users.filter(_.id === id).exists.result).map(exists => exists.asRight)
    }
  }

  /*This functions adds a failsafe mechanism to db request, given all slick operations are based in Scala's Future
  this mechanism is needed so when the Future fails it is then transformed to a Domain error*/
  def safeOperate[A](future: Result[A]): Result[A] = EitherT {
    future.value.recover({
      /*The ideal would be to process(Logging, Tracing and reporting) the error without crashing*/
      case _ => DomainError.notPerformed().asLeft
    })
  }

  private class UserTable(tag: Tag) extends Table[User](tag, "USERS") {


    def id = column[IdUser]("ID", O.PrimaryKey)

    def name = column[String]("NAME")

    def surname = column[String]("LASTNAME")

    def email = column[String]("EMAIL")


    def * = (id, name, surname, email) <> ((User.apply _).tupled, User.unapply)

  }

}
