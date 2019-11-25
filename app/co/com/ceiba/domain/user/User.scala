package co.com.ceiba.domain.user

case class User(id: User.IdUser, name: String, surname: String, email: String)

object User {
  type IdUser = String
}
