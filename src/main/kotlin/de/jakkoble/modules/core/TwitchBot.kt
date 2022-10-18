package de.jakkoble.modules.core

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import de.jakkoble.modules.events.ChannelMessageListener

object TwitchBot {
   private val twitchClient: TwitchClient = createClient()
   fun registerEvents() {
      val eventHandler = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)
      ChannelMessageListener(eventHandler)
   }
   fun start() {
      twitchClient.chat.joinChannel("Jakkoble")
   }
   private fun createClient(): TwitchClient = TwitchClientBuilder.builder()
      .withChatAccount(OAuth2Credential("twitch", System.getenv("TOKEN")))
      .withEnableChat(true)
      .build()
}