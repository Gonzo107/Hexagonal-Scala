package co.com.ceiba.events

abstract class Event {
  type T
  val tipo: String
  val payload: T
}
