package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.*

class EmoteCommand: TwitchCommand("emote") {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>): Boolean {
      if (args.isEmpty()) return false
      if (sender.id != "205919808" && sender.id != channel.id && TwitchBot.getMods(channel.name)?.contains(sender.name) == false) return false
      val channelData = channels.firstOrNull { it.userData.id == channel.id }
      val channelEmotes = channelData?.customEmotes
      if (args[0] == "list") {
         if (channelEmotes == null || channelEmotes.size == 0) {
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, you don't have any Emotes added yet.")
            return false
         }
         val message = StringBuilder()
         channelEmotes.forEach {
            message.append("$it ")
         }
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, these are the active Emotes: $message")
         return true
      }
      if (args.size != 2) return false
      val emoteName = args[1]
      when (args[0]) {
         "add" -> {
            val emotes = channelEmotes?.toMutableSet()
            if (emotes?.add(emoteName) == false) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, this Emote is already in your Emote List.")
               return false
            }
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote ' $emoteName ' got added to your Emote List.")
            getChannelDataByID(channel.id)?.update(ChannelData(
               userData = channel,
               enabled = channelData?.enabled ?: true,
               customEmotes = emotes?.toMutableList() ?: return false,
               writingChance = channelData.writingChance
            ))
         }
         "remove" -> {
            val emotes = channelEmotes?.toMutableSet()
            if (emotes?.remove(emoteName) == false) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, this Emote is not in your Emote List.")
               return false
            }
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote ' $emoteName ' got removed from your Emote List.")
            getChannelDataByID(channel.id)?.update(ChannelData(
               userData = channel,
               enabled = channelData?.enabled ?: true,
               customEmotes = emotes?.toMutableList() ?: return false,
               writingChance = channelData.writingChance
            ))
         }
      }
      return true
   }
}