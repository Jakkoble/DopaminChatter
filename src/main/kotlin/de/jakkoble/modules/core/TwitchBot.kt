package de.jakkoble.modules.core

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.helix.domain.User
import de.jakkoble.TOKEN
import de.jakkoble.modules.data.channels
import de.jakkoble.modules.events.ChannelMessageListener
import de.jakkoble.utils.ConsoleLogger

object TwitchBot {
   val twitchClient: TwitchClient = createClient()
   fun registerEvents() {
      ConsoleLogger.logInfo("Start registering Events...")
      val eventHandler = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)
      ChannelMessageListener().register(eventHandler)
   }
   fun start() {
      if (channels.isEmpty()) {
         ConsoleLogger.logWarning("No channel specified in channelData.json!")
         return
      }
      channels.forEach {
         if (it.userData.name != "dopaminchatter") {
            ConsoleLogger.logInfo("Joined Channel ${it.userData.displayName}")
            twitchClient.chat.joinChannel(it.userData.name)
         }
      }
      ConsoleLogger.logInfo("Successfully joined all Twitch Channels.")
   }
   private fun createClient(): TwitchClient {
      ConsoleLogger.logInfo("Start creating TwitchClient...")
      val client = TwitchClientBuilder.builder()
         .withChatAccount(OAuth2Credential("twitch", TOKEN))
         .withEnableChat(true)
         .withEnableHelix(true)
         .withEnableTMI(true)
         .build()
      ConsoleLogger.logInfo("TwitchClient successfully created.")
      return client
   }
   fun getChannel(name: String): User? = twitchClient.helix.getUsers(TOKEN, null, listOf(name)).execute().users.firstOrNull()
   fun getMods(channelName: String): List<String?>? = twitchClient.messagingInterface.getChatters(channelName).execute()?.moderators
}