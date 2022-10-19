package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.DataManager
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.channels
import de.jakkoble.utils.ConsoleLogger

class RemoveChannelCommand: TwitchCommand("removeChannel", true) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (args.isEmpty()) return
      val channelID = TwitchBot.getChannelID(args[0])
      if (channelID == null || channels.none { it.userData.id == channelID }) {
         ConsoleLogger.logWarning("No User Found with the Name ${args[0]}!")
         return
      }
      channels.removeIf { it.userData.id == channelID }
      ConsoleLogger.logInfo("Removed User ${args[0]} from Database.")
      DataManager.updateChannelData()
   }
}