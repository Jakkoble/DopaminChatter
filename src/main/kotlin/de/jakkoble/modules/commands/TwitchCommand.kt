package de.jakkoble.modules.commands

import de.jakkoble.modules.commands.cmd.ChannelCommand
import de.jakkoble.modules.commands.cmd.EnableCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.getChannelDataByID
import de.jakkoble.utils.ConsoleLogger

abstract class TwitchCommand(cmd: String) {
   val command: String = "#$cmd"
   init {
      ConsoleLogger.logInfo("Loaded $command Command.")
   }
   abstract fun onCommand(channel: UserData, sender: UserData, args: List<String>)
   fun executeCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (getChannelDataByID(channel.id)?.enabled == false && command != ChannelCommand().command && command != EnableCommand().command) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is currently disabled. With #enable you could activate the Bot again.")
         return
      }
      ConsoleLogger.logInfo("${channel.displayName}: User ${sender.displayName} executed $command Command.")
      onCommand(channel, sender, args.filter { it != "" })
   }
}