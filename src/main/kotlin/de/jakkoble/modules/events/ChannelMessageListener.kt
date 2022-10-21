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

class ChannelMessageListener(eventHandler: SimpleEventHandler) {
   init {
      eventHandler.onEvent(ChannelMessageEvent::class.java, this::onChannelMessage)
      ConsoleLogger.logInfo("Successfully registered ChannelMessageEvent.")
   }
   private fun onChannelMessage(event: ChannelMessageEvent) {
      val message = event.message.split(" ")
      if (message.isEmpty()) return
      if (commands.none { it.command == message.first() }) {
         handleEmote(getChannelDataByID(event.channel.id) ?: return, message)
         return
      }
      CommandManager().getCommand(message[0])?.executeCommand(
         channel = getChannelDataByID(event.channel.id)?.userData ?: return,
         sender = UserData(event.user.name, TwitchBot.getChannel(event.user.name)?.displayName ?: return, event.user.id),
         args = message.subList(1, message.size)
      )
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