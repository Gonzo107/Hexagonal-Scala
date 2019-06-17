package co.com.ceiba.aplicacion.commands

import co.com.ceiba.dominio.usuario.Usuario

case class EliminarUsuarioCommand(id: Usuario.IdUsuario) extends Command
