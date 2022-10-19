package de.jakkoble

import de.jakkoble.modules.commands.CommandManager
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.DataManager
import de.jakkoble.utils.ConsoleLogger
import java.io.File

fun main() {
   val startTime = System.currentTimeMillis()
   ConsoleLogger.logInfo("Started Bot in ${File(File(DataManager.filePath).absolutePath).parent}...")
   DataManager.load()
   val twitchBot = TwitchBot
   twitchBot.registerEvents()
   twitchBot.start()
   CommandManager().registerCommands()
   ConsoleLogger.logInfo("Successfully finished starting the Bot. (${System.currentTimeMillis() - startTime} ms)")
   ConsoleLogger.logInfo("Bot is now listening for Channel Events...")
}