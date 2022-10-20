package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.*

class ChanceCommand: TwitchCommand("chance", false) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (args.size != 1) return
      val chance = args[0]
      val data = getChannelDataByID(channel.id)
      data?.update(ChannelData(
         userData = channel,
         enabled = data.enabled,
         customEmotes = data.customEmotes,
         writingChance = chance.toInt()
      ))
      TwitchBot.twitchClient.chat.sendMessage(channel.name, "${sender.displayName}, you have set the WritingChance to $chance%.")
   }
}