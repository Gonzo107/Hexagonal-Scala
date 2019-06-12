package co.com.ceiba.services

import co.com.ceiba.usuario.Usuario

import scala.util.Try

trait RegistrarUsuarioUseCase {

  def registrar(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Try[Usuario]

}
