package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.channels
import de.jakkoble.utils.ConsoleLogger
import kotlin.system.exitProcess

class StopCommand: TwitchCommand("stop", true) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, you have stopped the Bot.")
      ConsoleLogger.logInfo("The Bot is now stopping...")
      channels.map { it.userData.name }.forEach { TwitchBot.twitchClient.chat.leaveChannel(it) }
      TwitchBot.twitchClient.chat.disconnect()
      TwitchBot.twitchClient.close()
      exitProcess(0)
   }
}