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
         channelEmotes.forEach { message.append("$it ") }
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, these are the active Emotes: $message")
         return true
      }
      if (args.size < 2) return false
      val emotes = args.subList(1, args.size)
      when (args[0]) {
         "add" -> {
            val currentEmotes = channelEmotes?.toMutableSet()
            if (emotes.size == 1) {
               if (currentEmotes?.add(emotes[0]) == true)
                  TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote ' ${args[1]} ' got added to your Emote List.")
               else TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote '  ${args[1]} ' is already in your Emote List.")
            } else {
               val addedEmotes = mutableListOf<String>()
               emotes.forEach { if (currentEmotes?.add(it) == true) addedEmotes.add(it) }
               if (addedEmotes.isEmpty())
                  TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, all of these Emotes are already in your Emote List.")
               else {
                  val message = StringBuilder()
                  emotes.forEach { message.append("$it ") }
                  TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, these Emotes got added to your Emote List: $message")
               }
            }
            getChannelDataByID(channel.id)?.update(ChannelData(
               userData = channel,
               enabled = channelData?.enabled ?: true,
               customEmotes = currentEmotes?.toMutableList() ?: return false,
               writingChance = channelData.writingChance
            ))
         }
         "remove" -> {
            val currentEmotes = channelEmotes?.toMutableSet()
            if (emotes.size == 1) {
               if (currentEmotes?.remove(emotes[0]) == true)
                  TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote ' ${args[1]} ' got removed from your Emote List.")
               else TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, the Emote '  ${args[1]} ' is not in your Emote List.")
            } else {
               val removedEmotes = mutableListOf<String>()
               emotes.forEach { if (currentEmotes?.remove(it) == true) removedEmotes.add(it) }
               if (removedEmotes.isEmpty())
                  TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, none of these Emotes is in your Emote List.")
               else {
                  val message = StringBuilder()
                  emotes.forEach { message.append("$it ") }
                  TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, these Emotes got removed from your Emote List: $message")
               }
            }
            getChannelDataByID(channel.id)?.update(ChannelData(
               userData = channel,
               enabled = channelData?.enabled ?: true,
               customEmotes = currentEmotes?.toMutableList() ?: return false,
               writingChance = channelData.writingChance
            ))
         }
      }
      return true
   }
}