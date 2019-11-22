package co.com.ceiba.infraestructura.driven.persistencia_h2.usuario


import cats.data.EitherT
import co.com.ceiba.domain.exception.DomainError
import co.com.ceiba.domain.usuario.Usuario.IdUsuario
import co.com.ceiba.domain.usuario.{Usuario, UsuarioRepository}
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import cats.implicits._
import co.com.ceiba.domain.execution.ExecutionDomain.Result

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class UsuarioRepositoryAdapter @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
(implicit executionContext: ExecutionContext)
  extends UsuarioRepository with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Usuarios = TableQuery[UserTable]

  override def save(usuario: Usuario): Result[Usuario] = EitherT {
    db.run(Usuarios += usuario).map({
      case 0 => DomainError.notPerformed().asLeft
      case _ => usuario.asRight
    })
  }


  override def getAll(): Result[Seq[Usuario]] = EitherT {
    db.run(Usuarios.result).map(usuario => usuario.asRight).recover(exception => DomainError.notPerformed().asLeft)
  }

  override def getById(id: IdUsuario): Future[Usuario] = db.run(Usuarios.filter(_.id === id).take(1).result.head)


  override def delete(id: IdUsuario): Future[IdUsuario] = db.run(Usuarios.filter(_.id === id).delete).map(_ => id)

  override def exists(id: IdUsuario): Future[Boolean] = db.run(Usuarios.filter(_.id === id).exists.result)

  private class UserTable(tag: Tag) extends Table[Usuario](tag, "USERS") {


    def id = column[IdUsuario]("ID", O.PrimaryKey)

    def nombre = column[String]("NAME")

    def apellido = column[String]("LASTNAME")

    def email = column[String]("EMAIL")


    def * = (id, nombre, apellido, email) <> ((Usuario.apply _).tupled, Usuario.unapply)

  }

}
