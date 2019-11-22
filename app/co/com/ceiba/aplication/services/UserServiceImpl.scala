package co.com.ceiba.aplication.services

import cats.data.EitherT
import cats.implicits._
import co.com.ceiba.domain.exception.DomainError
import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.services.UserService
import co.com.ceiba.domain.usuario.Usuario.IdUsuario
import co.com.ceiba.domain.usuario.{Usuario, UsuarioRepository}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl @Inject()(usuarios: UsuarioRepository)(implicit ec: ExecutionContext) extends UserService {
  override def delete(id: IdUsuario): Result[IdUsuario] = {
    for {
      exists <- usuarios.exists(id)
      deleted <- if (exists) {
        usuarios.delete(id)
      } else {
        EitherT.leftT[Future, Usuario.IdUsuario](DomainError.notExists())
      }
    } yield deleted

  }

  override def all(): Result[Seq[Usuario]] = usuarios.getAll()

  override def byId(id: IdUsuario) = usuarios.getById(id)

  override def create(id: IdUsuario, nombre: String, apellido: String, email: String): Result[Usuario] = {
    for {
      exists <- usuarios.exists(id)
      deleted <- if (!exists) {
        usuarios.save(Usuario(id, nombre, apellido, email))
      } else {
        EitherT.leftT[Future, Usuario](DomainError.exists())
      }
    } yield deleted

  }
}
