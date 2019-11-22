package co.com.ceiba.domain.execution

import cats.data.EitherT
import co.com.ceiba.domain.exception.DomainError

import scala.concurrent.Future

//Defines a common domain in which business logic is executed
object ExecutionDomain {
  type Result[ResultType] = EitherT[Future, DomainError, ResultType]
}
