package de.jakkoble

import de.jakkoble.modules.core.TwitchBot

fun main() {
   val twitchBot = TwitchBot
   twitchBot.registerEvents()
   twitchBot.start()
}