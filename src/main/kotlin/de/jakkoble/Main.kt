package de.jakkoble

import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.utils.ConsoleLogger

fun main() {
   val twitchBot = TwitchBot
   twitchBot.registerEvents()
   twitchBot.start()
   ConsoleLogger.logInfo("Bot is now Ready an is listening for ChannelEvents...")
}