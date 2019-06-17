package co.com.ceiba.aplicacion.events

case class FalloEvent(payload: String) extends Event {
  val tipo = "FALLO"
  override type T = String
}
