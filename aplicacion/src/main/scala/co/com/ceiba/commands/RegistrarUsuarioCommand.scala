package co.com.ceiba.commands

import co.com.ceiba.usuario.Usuario

case class RegistrarUsuarioCommand
(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String) extends Command