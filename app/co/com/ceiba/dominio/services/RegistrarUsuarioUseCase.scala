package co.com.ceiba.dominio.services

import co.com.ceiba.dominio.usuario.Usuario

import scala.concurrent.Future


trait RegistrarUsuarioUseCase {

  def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Future[Usuario]

}
