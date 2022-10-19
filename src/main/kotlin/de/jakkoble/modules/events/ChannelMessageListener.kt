package de.jakkoble.modules.events

import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import de.jakkoble.modules.commands.CommandManager
import de.jakkoble.modules.data.UserData
import de.jakkoble.utils.ConsoleLogger

class ChannelMessageListener(eventHandler: SimpleEventHandler) {
   init {
      eventHandler.onEvent(ChannelMessageEvent::class.java, this::onChannelMessage)
      ConsoleLogger.logInfo("Successfully registered ChannelMessageEvent.")
   }
   private fun onChannelMessage(event: ChannelMessageEvent) {
      val message = event.message.split(" ")
      if (message.isEmpty()) return
      if (event.channel.id != event.user.id || event.user.id != "205919808") return
      CommandManager().getCommand(message[0])?.executeCommand(
         channel = UserData(event.channel.name, event.channel.id),
         sender = UserData(event.user.name, event.user.id),
         args = message.subList(1, message.size)
      )
   }
}