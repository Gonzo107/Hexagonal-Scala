package co.com.ceiba.services

import co.com.ceiba.usuario.{Usuario, UsuarioRepository}

class ConsultarUsuarioService(usuarios: UsuarioRepository) extends ConsultarUsuariosUseCase {
  override def consultarTodos(): Seq[Usuario] = {
    usuarios.getAll()
  }

  override def consultarPorId(id: Usuario.IdUsuario): Option[Usuario] = {
    usuarios.getById(id)
  }
}
