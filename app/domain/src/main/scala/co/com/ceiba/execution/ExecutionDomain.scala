package co.com.ceiba.execution

import cats.data.EitherT
import co.com.ceiba.exception.DomainError

import scala.concurrent.Future

//Defines a common domain in which business logic is executed
object ExecutionDomain {

  /* A Result is any logic tha can produce an error in some point, so it is semantically
  represented as an Either (Which can be an error or a success) within a Future.
   EitherT is just a wrapper around Future[Either[A]] that lets you work directly in A without explicitly
   accessing the nested classes
   */
  type Result[ResultType] = EitherT[Future, DomainError, ResultType]
}
