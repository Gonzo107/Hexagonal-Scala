package co.com.ceiba.services

import co.com.ceiba.exceptions.NoExisteException
import co.com.ceiba.usuario.Usuario.IdUsuario
import co.com.ceiba.usuario.{Usuario, UsuarioRepository}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class EliminarUsuarioService  @Inject() (usuarios: UsuarioRepository)(implicit ec: ExecutionContext) extends EliminarUsuarioUseCase {

  override def eliminar(id: IdUsuario): Future[Usuario.IdUsuario] = {

    usuarios.exists(id).flatMap({
      case true => usuarios.delete(id)
      case false => Future.failed(NoExisteException())
    })

  }

}