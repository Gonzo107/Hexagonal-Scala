package co.com.ceiba.services

import co.com.ceiba.exceptions.YaExisteException
import co.com.ceiba.factory.UsuarioFactory
import co.com.ceiba.usuario.{Usuario, UsuarioRepository}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class RegistarUsuarioService @Inject()(usuarios: UsuarioRepository)(implicit ec: ExecutionContext) extends RegistrarUsuarioUseCase {

  override def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Future[Usuario] = {

    usuarios.exists(id).flatMap({
      case true => Future.failed(YaExisteException())
      case false => usuarios.save(UsuarioFactory.crear(id, nombre, apellido, email))
    })
  }
}
