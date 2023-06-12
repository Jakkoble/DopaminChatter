package de.jakkoble

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import de.jakkoble.modules.commands.CommandManager
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.DataManager
import de.jakkoble.utils.ConsoleLogger
import java.io.File
import kotlin.system.exitProcess
import org.slf4j.LoggerFactory

var TOKEN = ""
fun main(args: Array<String>) {
   TOKEN = args[0]
   if (TOKEN == "") {
      ConsoleLogger.logWarning("No TOKEN specified in program args.")
      exitProcess(1)
   }
   (LoggerFactory.getILoggerFactory().getLogger("com.netflix.config") as Logger).level = Level.OFF
   (LoggerFactory.getILoggerFactory().getLogger("io.github.xanthic.cache.core.CacheApiSettings") as Logger).level = Level.OFF
   (LoggerFactory.getILoggerFactory().getLogger("com.github.twitch4j.chat.TwitchChat") as Logger).level = Level.OFF
   val startTime = System.currentTimeMillis()
   ConsoleLogger.logInfo("Started Bot in ${File("").absolutePath}...")
   DataManager.load()
   val twitchBot = TwitchBot
   twitchBot.registerEvents()
   twitchBot.start()
   CommandManager().registerCommands()
   ConsoleLogger.logInfo("Successfully finished starting the Bot. (${System.currentTimeMillis() - startTime} ms)")
   ConsoleLogger.logInfo("Bot is now listening for Channel Events...")
}