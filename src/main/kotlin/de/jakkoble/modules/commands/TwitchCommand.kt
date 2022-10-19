package de.jakkoble.modules.commands

import de.jakkoble.modules.data.UserData
import de.jakkoble.utils.ConsoleLogger

abstract class TwitchCommand(cmd: String, ownerOnly: Boolean) {
   val command: String = "#$cmd"
   private val ownerCommand: Boolean = ownerOnly
   init {
      ConsoleLogger.logInfo("Successfully loaded $command Command.")
   }
   abstract fun onCommand(channel: UserData, sender: UserData, args: List<String>)
   fun executeCommand(channel: UserData, sender: UserData, args: List<String>) {
      if (ownerCommand && sender.id != "205919808") return
      ConsoleLogger.logInfo("${channel.name.first().uppercase()}: User ${sender.name} executed $command Command.")
      onCommand(channel, sender, args)
   }
}