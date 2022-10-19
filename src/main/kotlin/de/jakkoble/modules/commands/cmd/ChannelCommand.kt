package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.*
import de.jakkoble.utils.ConsoleLogger

class ChannelCommand : TwitchCommand("channel", true) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (args.size != 2) return
      val channelData = getChannelDataByID(channel.id)
      val targetName = args[1]
      val targetID = TwitchBot.getChannelID(targetName)
      if (targetID == null) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, could not find a Channel called '$targetName'.")
         return
      }
      val target = UserData(targetName.lowercase(), targetID)
      when (args[0]) {
         "add" -> {
            if (channels.any { it.userData.id == targetID }) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' is already in the Channel List.")
               return
            }
            channels.add(ChannelData(target))
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' was successfully added.")
            DataManager.updateChannelData()
         }
         "remove" -> {
            if (!channels.removeIf { it.userData.id == targetID }) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' is not in the Channel List.")
               return
            }
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' was successfully removed.")
            DataManager.updateChannelData()
         }
         "disable" -> {
            if (channelData == null) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, there is no ChannelData for Channel '${channel.name}'.")
               return
            }
            if (!channelData.enabled) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, I am already disabled for '${channel.name}'.")
               return
            }
            channelData.update(ChannelData(
               userData = channel,
               enabled = false,
               customEmotes = channelData.customEmotes,
               writingChance = channelData.writingChance)
            )
            TwitchBot.twitchClient.chat.joinChannel(channel.name)
            ConsoleLogger.logInfo("Disabled Bot for Channel '${channel.name}'.")
         }
         "enable" -> {
            if (channelData == null) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, there is no ChannelData for Channel '${channel.name}'.")
               return
            }
            if (!channelData.enabled) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, I am already enabled for '${channel.name}'.")
               return
            }
            channelData.update(ChannelData(
               userData = channel,
               customEmotes = channelData.customEmotes,
               writingChance = channelData.writingChance)
            )
            TwitchBot.twitchClient.chat.leaveChannel(channel.name)
            ConsoleLogger.logInfo("Enabled Bot for Channel '${channel.name}'.")
         }
      }
   }
}