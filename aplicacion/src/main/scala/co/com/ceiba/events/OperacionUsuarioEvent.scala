package co.com.ceiba.events

import co.com.ceiba.usuario.Usuario

case class OperacionUsuarioEvent(payload: Usuario) extends Event {
  val tipo = "EXITO"
  override type T = Usuario
}
