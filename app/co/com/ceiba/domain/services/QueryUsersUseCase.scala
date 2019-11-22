package co.com.ceiba.domain.services

import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.usuario.Usuario

import scala.concurrent.Future


protected trait QueryUsersUseCase {

  def all(): Result[Future[Seq[Usuario]]]

  def byId(id: Usuario.IdUsuario): Result[Future[Usuario]]

}
