package de.jakkoble.modules.core

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import de.jakkoble.modules.data.channels
import de.jakkoble.modules.events.ChannelMessageListener
import de.jakkoble.utils.ConsoleLogger

object TwitchBot {
   private val twitchClient: TwitchClient = createClient()
   fun registerEvents() {
      ConsoleLogger.logInfo("Start registering Events...")
      val eventHandler = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)
      ChannelMessageListener(eventHandler)
      ConsoleLogger.logInfo("Successfully registered all Events.")
   }
   fun start() {
      ConsoleLogger.logInfo("Start joining Channels...")
      if (channels.isEmpty()) {
         ConsoleLogger.logWarning("No channel specified in channelData.json!")
         return
      }
      channels.forEach { twitchClient.chat.joinChannel(it.channelName) }
      ConsoleLogger.logInfo("Successfully joined all Twitch Channels.")
   }
   private fun createClient(): TwitchClient {
      ConsoleLogger.logInfo("Start creating TwitchClient...")
      val client = TwitchClientBuilder.builder()
         .withChatAccount(OAuth2Credential("twitch", System.getenv("TOKEN")))
         .withEnableChat(true)
         .build()
      ConsoleLogger.logInfo("TwitchClient successfully created.")
      return client
   }
}