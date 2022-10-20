package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.*
import de.jakkoble.utils.ConsoleLogger

class ChannelCommand : TwitchCommand("channel", true) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (args.size == 1 && args[0] == "list") {
         val channelNames = channels.map { it.userData.name }
         val message = StringBuilder()
         channelNames.forEach {
            message.append(if (channelNames.indexOf(it) != channelNames.size - 1) "$it, " else it)
         }
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, these Channels are all registered Channels: ${message.substring(0)}")
         return
      }
      if (args.size != 2) return
      val targetName = args[1]
      val targetUser = TwitchBot.getChannel(targetName)
      if (targetUser == null) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, could not find a Channel called '$targetName'.")
         return
      }
      val target = UserData(targetUser.login, targetUser.displayName, targetUser.id)
      val targetData = getChannelDataByID(targetUser.id)
      when (args[0]) {
         "add" -> {
            if (channels.any { it.userData.id == targetUser.id }) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' is already in the Channel List.")
               return
            }
            channels.add(ChannelData(target))
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' was successfully added.")
            DataManager.updateChannelData()
         }
         "remove" -> {
            if (!channels.removeIf { it.userData.id == targetUser.id }) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' is not in the Channel List.")
               return
            }
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, the Channel '$targetName' was successfully removed.")
            DataManager.updateChannelData()
         }
         "disable" -> {
            if (targetData == null) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, there is no ChannelData for Channel '${target.name}'.")
               return
            }
            if (!targetData.enabled) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, I am already disabled for '${target.name}'.")
               return
            }
            targetData.update(ChannelData(
               userData = target,
               enabled = false,
               customEmotes = targetData.customEmotes,
               writingChance = targetData.writingChance)
            )
            TwitchBot.twitchClient.chat.leaveChannel(target.name)
            ConsoleLogger.logInfo("Disabled Bot for Channel '${target.name}'.")
         }
         "enable" -> {
            if (targetData == null) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, there is no ChannelData for Channel '${target.name}'.")
               return
            }
            if (targetData.enabled) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.name}, I am already enabled for '${target.name}'.")
               return
            }
            targetData.update(ChannelData(
               userData = target,
               customEmotes = targetData.customEmotes,
               writingChance = targetData.writingChance)
            )
            TwitchBot.twitchClient.chat.joinChannel(target.name)
            ConsoleLogger.logInfo("Enabled Bot for Channel '${target.name}'.")
         }
      }
   }
}