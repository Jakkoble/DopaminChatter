package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.*

class EmoteCommand: TwitchCommand("emote", false) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      val channelEmotes = channels.flatMap { it.customEmotes }
      if (args.size == 1 && args[0] == "list") {
         val message = StringBuilder()
         channelEmotes.forEach {
            message.append(if (channelEmotes.indexOf(it) != channelEmotes.size - 1) "$it, " else it)
         }
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, these are the active Emotes: ${message.substring(0)}")
         return
      }
      if (args.size != 2) return
      val emoteName = args[1]
      when (args[0]) {
         "add" -> {
            val emotes = channelEmotes.toMutableSet()
            if (!emotes.add(emoteName)) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, this Emote is already in your Emote List.")
               return
            }
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote ' $emoteName ' got added to your Emote List.")
            getChannelDataByID(channel.id)?.update(ChannelData(
               userData = channel,
               customEmotes = emotes.toMutableList()
            ))
         }
         "remove" -> {
            val emotes = channelEmotes.toMutableSet()
            if (!emotes.remove(emoteName)) {
               TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, this Emote is not in your Emote List.")
               return
            }
            TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote ' $emoteName ' got removed from your Emote List.")
            getChannelDataByID(channel.id)?.update(ChannelData(
               userData = channel,
               customEmotes = emotes.toMutableList()
            ))
         }
      }
   }
}