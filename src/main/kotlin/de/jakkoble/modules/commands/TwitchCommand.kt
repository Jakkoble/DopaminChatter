package de.jakkoble.modules.commands

import de.jakkoble.modules.data.UserData

abstract class TwitchCommand(cmd: String) {
   val command: String = cmd
   abstract fun onCommand(channel: UserData, userData: UserData, args: List<String>)
   fun executeCommand(channel: UserData, userData: UserData, args: List<String>) = onCommand(channel, userData, args)
}