package co.com.ceiba.domain.user

//This case class represents the basic data needed so a User can exist
case class UserSeed(id: User.IdUser, name: String, surname: String, email: String)
