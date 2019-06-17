package co.com.ceiba.aplicacion.services

import co.com.ceiba.aplicacion.factory.UsuarioFactory
import co.com.ceiba.dominio.exceptions.YaExisteException
import co.com.ceiba.dominio.services.RegistrarUsuarioUseCase
import co.com.ceiba.dominio.usuario.{Usuario, UsuarioRepository}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class RegistrarUsuarioService @Inject()(usuarios: UsuarioRepository)(implicit ec: ExecutionContext) extends RegistrarUsuarioUseCase {

  override def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Future[Usuario] = {

    usuarios.exists(id).flatMap({
      case true => Future.failed(YaExisteException(RegistrarUsuarioService.USUARIO_YA_EXISTE))
      case false => usuarios.save(UsuarioFactory.crear(id, nombre, apellido, email))
    })
  }
}

object RegistrarUsuarioService {
  val USUARIO_YA_EXISTE = "Ya existe un usuario con el id ingresado"
}
