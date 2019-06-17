package co.com.ceiba.services

import co.com.ceiba.usuario.Usuario

import scala.concurrent.Future

trait RegistrarUsuarioUseCase {

  def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Future[Usuario]

}
