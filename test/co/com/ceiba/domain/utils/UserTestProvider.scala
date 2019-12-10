package co.com.ceiba.domain.utils

import co.com.ceiba.user.{User, UserSeed}

object UserTestProvider {

  val TEST_ID = "1036549877"
  val TEST_NAME = "Andres"
  val TEST_SURNAME = "Gonzalez"
  val TEST_EMAIL = "andres.gonzalez@correo.com"

  def aUser(): User = {
    User(TEST_ID, TEST_NAME, TEST_SURNAME, TEST_EMAIL)
  }

  def aSeed(): UserSeed = {
    UserSeed(TEST_ID, TEST_NAME, TEST_SURNAME, TEST_EMAIL)
  }
}
