package co.com.ceiba.domain.services

import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.usuario.Usuario

import scala.concurrent.Future

protected trait EliminarUsuarioUseCase {

  def eliminar(id: Usuario.IdUsuario): Result[Future[Usuario.IdUsuario]]

}
