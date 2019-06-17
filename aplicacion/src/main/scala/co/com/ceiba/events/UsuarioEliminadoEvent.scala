package co.com.ceiba.events

import co.com.ceiba.usuario.Usuario

case class UsuarioEliminadoEvent(payload: Usuario.IdUsuario) extends Event {
  val tipo = "EXITO"
  override type T = Usuario.IdUsuario
}
