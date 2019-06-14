package co.com.ceiba.usuario

import co.com.ceiba.usuario.Usuario.IdUsuario
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class UsuarioRepositoryAdapter @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
(implicit executionContext: ExecutionContext)
  extends UsuarioRepository with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Usuarios = TableQuery[UserTable]

  override def save(usuario: Usuario): Future[Usuario] = db.run(Usuarios += usuario).map(_ => usuario)

  override def getAll(): Future[Seq[Usuario]] = db.run(Usuarios.result)

  override def getById(id: IdUsuario): Future[Usuario] = db.run(Usuarios.filter(_.id == id).take(1).result.head)


  override def delete(id: IdUsuario): Future[IdUsuario] = db.run(Usuarios.filter(_.id == id).delete).map(_ => id)

  override def exists(id: IdUsuario): Future[Boolean] = db.run(Usuarios.filter(_.id == id).exists.result)

  private class UserTable(tag: Tag) extends Table[Usuario](tag, "USERS") {


    def id = column[IdUsuario]("ID", O.PrimaryKey)

    def nombre = column[String]("NAME")

    def apellido = column[String]("LASTNAME")

    def email = column[String]("EMAIL")


    def * = (id, nombre, apellido, email) <> ((Usuario.apply _).tupled, Usuario.unapply)

  }

}
