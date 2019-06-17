package co.com.ceiba.dominio.services

import co.com.ceiba.dominio.usuario.Usuario

import scala.concurrent.Future

trait ConsultarUsuariosUseCase {

  def consultarTodos(): Future[Seq[Usuario]]

  def consultarPorId(id: String): Future[Usuario]

}
