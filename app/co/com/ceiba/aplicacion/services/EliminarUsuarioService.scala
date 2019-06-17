package co.com.ceiba.aplicacion.services

import co.com.ceiba.dominio.exceptions.NoExisteException
import co.com.ceiba.dominio.services.EliminarUsuarioUseCase
import co.com.ceiba.dominio.usuario.Usuario.IdUsuario
import co.com.ceiba.dominio.usuario.{Usuario, UsuarioRepository}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class EliminarUsuarioService @Inject()(usuarios: UsuarioRepository)(implicit ec: ExecutionContext) extends EliminarUsuarioUseCase {

  override def eliminar(id: IdUsuario): Future[Usuario.IdUsuario] = {

    usuarios.exists(id).flatMap({
      case true => usuarios.delete(id)
      case false => Future.failed(NoExisteException(EliminarUsuarioService.USUARIO_NO_EXISTENTE))
    })

  }

}

object EliminarUsuarioService {
  val USUARIO_NO_EXISTENTE = "No se puede eliminar un usuario no existente"
}