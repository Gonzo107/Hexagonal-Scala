package co.com.ceiba.aplicacion.events

import co.com.ceiba.dominio.usuario.Usuario

case class ExitoUsuarioEvent(payload: Usuario) extends Event {
  val tipo = "EXITO"
  override type T = Usuario
}
