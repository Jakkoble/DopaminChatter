package de.jakkoble.modules.commands

import de.jakkoble.modules.commands.cmd.AddChannelCommand

private val commands: MutableList<TwitchCommand> = mutableListOf()
class CommandManager {
   fun registerCommands() {
      commands.add(AddChannelCommand())
   }
   fun getCommand(command: String): TwitchCommand? = commands.firstOrNull { it.command == command }
}