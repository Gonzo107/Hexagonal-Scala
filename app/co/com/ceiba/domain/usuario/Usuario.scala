package co.com.ceiba.domain.usuario

case class Usuario(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String)

object Usuario {
  type IdUsuario = String
}
