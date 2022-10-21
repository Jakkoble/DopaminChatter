package de.jakkoble.modules.commands

import de.jakkoble.modules.commands.cmd.ChannelCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.getChannelDataByID
import de.jakkoble.utils.ConsoleLogger

abstract class TwitchCommand(cmd: String, ownerOnly: Boolean) {
   val command: String = "#$cmd"
   private val ownerCommand: Boolean = ownerOnly
   init {
      ConsoleLogger.logInfo("Loaded $command Command.")
   }
   abstract fun onCommand(channel: UserData, sender: UserData, args: List<String>)
   fun executeCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (!command.contains("register") && !command.contains("help")) {
         if ((ownerCommand && sender.id != "205919808") || (channel.id != sender.id && sender.id != "205919808")) {
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, you don't have enough permission to execute this Command!")
            return
         }
         if (getChannelDataByID(channel.id)?.enabled == false && command != ChannelCommand().command) {
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is currently disabled. Ask Jakkoble to enable it again.")
            return
         }
      }
      ConsoleLogger.logInfo("${channel.displayName}: User ${sender.displayName} executed $command Command.")
      onCommand(channel, sender, args.filter { it != "" })
   }
}