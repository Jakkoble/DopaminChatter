package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.data.UserData

class EmoteCommand: TwitchCommand("emote", false) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {
      TODO("Not yet implemented")
   }
}