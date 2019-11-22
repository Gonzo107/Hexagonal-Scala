package co.com.ceiba.domain.services

import co.com.ceiba.domain.execution.ExecutionDomain.Result
import co.com.ceiba.domain.usuario.Usuario

import scala.concurrent.Future


protected trait RegistrarUsuarioUseCase {

  def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Result[Future[Usuario]]

}
