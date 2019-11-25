package co.com.ceiba.aplication.factory

import co.com.ceiba.domain.user.User
import co.com.ceiba.domain.user.User.IdUser

object UserFactory {

  def create(id: IdUser, name: String, surname: String, email: String): User =
    User(id, name, surname, email)
}
