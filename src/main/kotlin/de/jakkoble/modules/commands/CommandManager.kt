package de.jakkoble.modules.commands

import de.jakkoble.modules.commands.cmd.ChanceCommand
import de.jakkoble.modules.commands.cmd.ChannelCommand
import de.jakkoble.modules.commands.cmd.EmoteCommand
import de.jakkoble.modules.commands.cmd.StopCommand

val commands: MutableList<TwitchCommand> = mutableListOf()
class CommandManager {
   fun registerCommands() {
      commands.add(ChannelCommand())
      commands.add(EmoteCommand())
      commands.add(StopCommand())
      commands.add(ChanceCommand())
   }
   fun getCommand(command: String): TwitchCommand? = commands.firstOrNull { it.command.equals(command, true) }
}