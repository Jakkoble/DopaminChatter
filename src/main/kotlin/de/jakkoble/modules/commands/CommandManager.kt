package de.jakkoble.modules.commands

import de.jakkoble.modules.commands.cmd.AddChannelCommand
import de.jakkoble.modules.commands.cmd.RemoveChannelCommand

private val commands: MutableList<TwitchCommand> = mutableListOf()
class CommandManager {
   fun registerCommands() {
      commands.add(AddChannelCommand())
      commands.add(RemoveChannelCommand())
   }
   fun getCommand(command: String): TwitchCommand? = commands.firstOrNull { it.command.equals(command, true) }
}