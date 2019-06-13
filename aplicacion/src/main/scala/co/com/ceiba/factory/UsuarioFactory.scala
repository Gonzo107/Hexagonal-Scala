package co.com.ceiba.factory

import co.com.ceiba.usuario.Usuario

object UsuarioFactory {

  def crear(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Usuario = {

    return Usuario(id, nombre, apellido, email)
  }
}
