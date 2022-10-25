package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.ChannelData
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.getChannelDataByID
import de.jakkoble.modules.data.update
import de.jakkoble.utils.ConsoleLogger

class EnableCommand: TwitchCommand("enable") {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>): Boolean {
      if (sender.id != "205919808" && sender.id != channel.id && TwitchBot.getMods(channel.name)?.contains(sender.name) == false) return false
      val channelData = getChannelDataByID(channel.id) ?: return false
      channelData.update(
         ChannelData(
         userData = channel,
         customEmotes = channelData.customEmotes,
         writingChance = channelData.writingChance)
      )
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Bot is now enabled.")
      ConsoleLogger.logInfo("Enabled Bot for Channel '${channel.name}'.")
      return true
   }
}