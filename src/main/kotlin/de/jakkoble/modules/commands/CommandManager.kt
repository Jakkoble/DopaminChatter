package de.jakkoble.modules.commands

import de.jakkoble.modules.commands.cmd.*

val commands: MutableList<TwitchCommand> = mutableListOf()
class CommandManager {
   fun registerCommands() {
      commands.add(ChannelCommand())
      commands.add(EmoteCommand())
      commands.add(StopCommand())
      commands.add(ChanceCommand())
      commands.add(RegisterCommand())
   }
   fun getCommand(command: String): TwitchCommand? = commands.firstOrNull { it.command.equals(command, true) }
}