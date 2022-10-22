package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.*
import java.util.regex.Pattern

class ChannelCommand : TwitchCommand("channel", true) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (args[0] == "list") {
         val channelNames = channels.map { it.userData.displayName }
         val message = StringBuilder()
         channelNames.forEach {
            message.append(if (channelNames.indexOf(it) != channelNames.size - 1) "$it, " else it)
         }
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, these are all the registered Channels: $message")
         return
      }
      if (args.size != 2) return
      val targetName = args[1]
      val withSpecialCharacters = Pattern.compile("[^A-Za-z0-9_]").matcher(targetName).find()
      val targetUser = if (!withSpecialCharacters) TwitchBot.getChannel(targetName) else null
      if (targetUser == null) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, could not find a Channel called '$targetName'.")
         return
      }
      val target = UserData(targetUser.login, targetUser.displayName, targetUser.id)
      when (args[0]) {
         "add" -> {
            if (channels.any { it.userData.id == targetUser.id }) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Channel '$targetName' is already in the Channel List.")
               return
            }
            channels.add(ChannelData(target))
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Channel '$targetName' was successfully added.")
            TwitchBot.twitchClient.chat.joinChannel(targetName)
            TwitchBot.twitchClient.chat.sendMessage(targetName, "Hey! I am now active on your Channel. Type #help for Command Usage.")
            DataManager.updateChannelData()
         }
         "remove" -> {
            if (target.id == "205919808") {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, you could not remove the Owner Channel!")
               return
            }
            if (!channels.removeIf { it.userData.id == targetUser.id }) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Channel '$targetName' is not in the Channel List.")
               return
            }
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Channel '$targetName' was successfully removed.")
            TwitchBot.twitchClient.chat.leaveChannel(targetName)
            DataManager.updateChannelData()
         }
      }
   }
}