package de.jakkoble.modules.events

import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import de.jakkoble.modules.commands.CommandManager
import de.jakkoble.modules.commands.commands
import de.jakkoble.modules.core.TwitchBot
import de.jakkoble.modules.data.ChannelData
import de.jakkoble.modules.data.UserData
import de.jakkoble.modules.data.getChannelDataByID
import de.jakkoble.utils.ConsoleLogger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ChannelMessageListener {
   suspend fun register(eventHandler: SimpleEventHandler) = coroutineScope {
      eventHandler.onEvent(ChannelMessageEvent::class.java) { launch { onChannelMessage(it) } }
      ConsoleLogger.logInfo("Successfully registered ChannelMessageEvent.")
   }
   private suspend fun onChannelMessage(event: ChannelMessageEvent) {
      coroutineScope {
         val message = event.message.split(" ")
         if (message.isEmpty()) return@coroutineScope
         if (commands.none { it.command == message.first() }) {
            handleEmote(getChannelDataByID(event.channel.id) ?: return@coroutineScope, message)
            return@coroutineScope
         }
         launch {
            CommandManager().getCommand(message[0])?.executeCommand(
               channel = getChannelDataByID(event.channel.id)?.userData ?: return@launch,
               sender = UserData(event.user.name, TwitchBot.getChannel(event.user.name)?.displayName ?: return@launch, event.user.id),
               args = message.subList(1, message.size)
            )
         }
      }
   }
   private fun handleEmote(channelData: ChannelData, message: List<String>) {
      if (!channelData.enabled) return
      val emoteList = channelData.customEmotes
      message.forEach {
         if (emoteList.contains(it)) {
            if ((1..100).random() <= channelData.writingChance)
               TwitchBot.twitchClient.chat.sendMessage(channelData.userData.name, it)
            return
         }
      }
   }
}