package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.channels

class RegisterCommand: TwitchCommand("register", false) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (channel.id != "205919808") {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, these Command is only available in Jakkoble's Twitch Chat ( https://twitch.tv/jakkoble )")
         return
      }
      val channelData = channels.firstOrNull { it.userData.id == channel.id }
      if (channelData != null) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is already added to your Channel. Type #unregister to remove it.")
         return
      }
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is now added to your Channel.")
   }
}