package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.ChannelData
import de.jakkoble.modules.data.DataManager
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.channels

class RegisterCommand: TwitchCommand("register", false) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (channel.id != "205919808") {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, this Command is only available in Jakkoble's Twitch Chat ( https://twitch.tv/jakkoble )")
         return
      }
      val channelData = channels.firstOrNull { it.userData.id == sender.id }
      if (channelData != null) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is already added to your Channel. Type #unregister to remove it.")
         return
      }
      channels.add(ChannelData(sender))
      DataManager.updateChannelData()
      TwitchBot.twitchClient.chat.joinChannel(sender.name)
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is now added to your Channel.")
      TwitchBot.twitchClient.chat.sendMessage(sender.name, "Hey! I am now active on your Channel. Type #help for Command Usage.")
   }
}