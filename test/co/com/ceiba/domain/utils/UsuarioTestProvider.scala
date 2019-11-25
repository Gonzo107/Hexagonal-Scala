package co.com.ceiba.domain.utils

import co.com.ceiba.domain.user.User

object UsuarioTestProvider {

  val ID_PRUEBA = "1036549877"
  val NOMBRE_PRUEBA = "Andres"
  val APELLIDO_PRUEBA = "Gonzalez"
  val EMAIL_PRUEBA = "andres.gonzalez@correo.com"

  def unUsuario(): User = {
    User(ID_PRUEBA, NOMBRE_PRUEBA, APELLIDO_PRUEBA, EMAIL_PRUEBA)
  }
}
