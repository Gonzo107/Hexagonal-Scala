package co.com.ceiba.aplicacion.factory

import co.com.ceiba.dominio.usuario.Usuario

object UsuarioFactory {

  def crear(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String): Usuario = {

    return Usuario(id, nombre, apellido, email)
  }
}
