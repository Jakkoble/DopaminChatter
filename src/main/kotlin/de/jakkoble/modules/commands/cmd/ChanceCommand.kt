package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.ChannelData
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.getChannelDataByID
import de.jakkoble.modules.data.update

class ChanceCommand: TwitchCommand("chance") {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>): Boolean {
      if (args.size != 1) return false
      if (sender.id != "205919808" && sender.id != channel.id && TwitchBot.getMods(channel.id)?.contains(sender.id) == false) return false
      val chance = args[0].toIntOrNull()
      if (chance == null || chance !in 1..100) {
         TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, you have to choose a value betweet 1-100!")
         return false
      }
      val data = getChannelDataByID(channel.id)
      data?.update(ChannelData(
         userData = channel,
         enabled = data.enabled,
         customEmotes = data.customEmotes,
         writingChance = chance
      ))
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, you have set the WritingChance to $chance%.")
      return true
   }
}