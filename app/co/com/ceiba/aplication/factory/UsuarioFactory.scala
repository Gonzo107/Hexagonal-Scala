package co.com.ceiba.aplication.factory

import co.com.ceiba.domain.usuario.Usuario

object UsuarioFactory {

  def crear(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Usuario = {

    return Usuario(id, nombre, apellido, email)
  }
}
