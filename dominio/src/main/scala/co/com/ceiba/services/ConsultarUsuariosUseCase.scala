package co.com.ceiba.services

import co.com.ceiba.usuario.Usuario

import scala.concurrent.Future

trait ConsultarUsuariosUseCase {

  def consultarTodos(): Future[Seq[Usuario]]

  def consultarPorId(id: String): Future[Usuario]

}
