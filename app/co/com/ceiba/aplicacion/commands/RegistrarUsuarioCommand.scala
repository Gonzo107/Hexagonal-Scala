package co.com.ceiba.aplicacion.commands

import co.com.ceiba.dominio.usuario.Usuario

case class RegistrarUsuarioCommand
(id: Usuario.IdUsuario, nombre: String, apellido: String, email: String) extends Command