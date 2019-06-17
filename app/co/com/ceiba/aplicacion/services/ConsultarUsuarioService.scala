package co.com.ceiba.aplicacion.services

import co.com.ceiba.dominio.services.ConsultarUsuariosUseCase
import co.com.ceiba.dominio.usuario.{Usuario, UsuarioRepository}
import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class ConsultarUsuarioService @Inject()(usuarios: UsuarioRepository)(implicit ec: ExecutionContext) extends ConsultarUsuariosUseCase {
  override def consultarTodos(): Future[Seq[Usuario]] = {
    usuarios.getAll()
  }

  override def consultarPorId(id: Usuario.IdUsuario): Future[Usuario] = {
    usuarios.getById(id)
  }
}
