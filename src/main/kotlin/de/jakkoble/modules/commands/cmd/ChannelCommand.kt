package de.jakkoble.modules.commands.cmd

import de.jakkoble.modules.commands.TwitchCommand
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.ChannelData
import de.jakkoble.modules.data.DataManager
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.channels
import de.jakkoble.utils.ConsoleLogger

class ChannelCommand : TwitchCommand("channel", true) {
   override fun onCommand(channel: UserData, sender: UserData, args: List<String>) {

   }
}