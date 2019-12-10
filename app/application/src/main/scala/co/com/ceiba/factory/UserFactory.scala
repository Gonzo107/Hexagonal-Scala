package co.com.ceiba.factory

import co.com.ceiba.user.{User, UserSeed}


object UserFactory {

  def from(seed: UserSeed): User =
    User(seed.id, seed.name, seed.surname, seed.email)
}
