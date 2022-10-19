package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.ChannelData
import de.jakkoble.modules.data.DataManager
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.channels
import de.jakkoble.utils.ConsoleLogger

class AddChannelCommand : TwitchCommand("addChannel", true) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (channel.id != "205919808") return
      if (args.isEmpty()) return
      val channelID = TwitchBot.getChannelID(args[0])
      if (channelID == null) {
         ConsoleLogger.logWarning("No User Found with the Name ${args[0]}!")
         return
      }
      val target = UserData(args[0].lowercase(), channelID)
      if (channels.any { it.userData.id == target.id }) {
         ConsoleLogger.logWarning("User ${args[0]} already exists.")
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the User already exists in Database.")
         return
      }
      ConsoleLogger.logInfo("Added User ${target.name} to Database.")
      channels.add(ChannelData(target))
      DataManager.updateChannelData()
   }
}