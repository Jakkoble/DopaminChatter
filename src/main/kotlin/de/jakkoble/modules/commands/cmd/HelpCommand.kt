package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.UserData

class HelpCommand: TwitchCommand("help") {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>): Boolean {
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, visit the Bot on GitHub for help: https://bit.ly/DopaminChatter")
      return true
   }
}