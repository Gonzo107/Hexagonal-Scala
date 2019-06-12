package co.com.ceiba.services

import co.com.ceiba.usuario.Usuario

trait ConsultarUsuariosUseCase {

  def consultarTodos(): Seq[Usuario]

  def consultarPorId(id: String): Option[Usuario]

}
