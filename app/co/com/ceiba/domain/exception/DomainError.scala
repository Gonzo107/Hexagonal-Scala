package co.com.ceiba.domain.exception

//Defines a common trait for all domain errors within the app
sealed trait DomainError extends Exception with Product with Serializable

object DomainError {

  //Semantically communicates that something already exists
  final case class AlreadyExists() extends DomainError

  //Semantically communicates that something does not exists
  final case class NotExists() extends DomainError

  //Semantically communicates that something does not exists
  final case class NotPerformed() extends DomainError


  def notExists(): DomainError = NotExists()

  def exists(): DomainError = AlreadyExists()

  def notPerformed(): DomainError = NotPerformed()

}