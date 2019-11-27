package co.com.ceiba.aplication.factory

import co.com.ceiba.domain.user.{User, UserSeed}

object UserFactory {

  def from(seed: UserSeed): User =
    User(seed.id, seed.name, seed.surname, seed.email)
}
