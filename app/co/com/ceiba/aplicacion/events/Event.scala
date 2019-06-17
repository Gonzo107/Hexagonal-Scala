package co.com.ceiba.aplicacion.events

abstract class Event {
  type T
  val tipo: String
  val payload: T
}
