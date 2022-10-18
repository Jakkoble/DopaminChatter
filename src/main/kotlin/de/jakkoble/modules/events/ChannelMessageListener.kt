package de.jakkoble.modules.events

import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

class ChannelMessageListener(eventHandler: SimpleEventHandler) {
   init {
      eventHandler.onEvent(ChannelMessageEvent::class.java, this::onChannelMessage)
   }
   private fun onChannelMessage(event: ChannelMessageEvent) {
      event.twitchChat.sendMessage(event.channel.name, event.message)
   }
}