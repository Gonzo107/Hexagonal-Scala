package co.com.ceiba.commands

import co.com.ceiba.usuario.Usuario

case class EliminarUsuarioCommand(id: Usuario.IdUsuario) extends Command
