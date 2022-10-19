package de.jakkoble.modules.commands

import de.jakkoble.modules.commands.cmd.ChannelCommand

private val commands: MutableList<TwitchCommand> = mutableListOf()
class CommandManager {
   fun registerCommands() {
      commands.add(ChannelCommand())
   }
   fun getCommand(command: String): TwitchCommand? = commands.firstOrNull { it.command.equals(command, true) }
}