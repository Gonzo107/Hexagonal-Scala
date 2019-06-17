package co.com.ceiba.aplicacion.events

import co.com.ceiba.dominio.usuario.Usuario

case class UsuarioEliminadoEvent(payload: Usuario.IdUsuario) extends Event {
  val tipo = "EXITO"
  override type T = Usuario.IdUsuario
}
