package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.DataManager
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.channels
import java.io.File

class UnregisterCommand: TwitchCommand("unregister", false) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (channel.id != "205919808") {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, this Command is only available in Jakkoble's Twitch Chat ( https://twitch.tv/jakkoble )")
         return
      }
      val channelData = channels.firstOrNull { it.userData.id == sender.id }
      if (channelData == null) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is already removed from your Channel. Type #register to add it again.")
         return
      }
      channels.remove(channelData)
      DataManager.updateChannelData()
      TwitchBot.twitchClient.chat.leaveChannel(sender.name)
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is now removed from your Channel.")
      File("${DataManager.filePath}/${sender.id}.json").delete()
   }
}