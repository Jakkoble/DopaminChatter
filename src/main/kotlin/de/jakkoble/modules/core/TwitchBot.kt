package de.jakkoble.modules.core

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.helix.domain.Moderator
import com.github.twitch4j.helix.domain.User
import de.jakkoble.modules.data.channels
import de.jakkoble.modules.events.ChannelMessageListener
import de.jakkoble.utils.ConsoleLogger
import kotlinx.coroutines.coroutineScope

object TwitchBot {
   val twitchClient: TwitchClient = createClient()
   suspend fun registerEvents() = coroutineScope {
      ConsoleLogger.logInfo("Start registering Events...")
      val eventHandler = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)
      ChannelMessageListener().register(eventHandler)
   }
   suspend fun start() = coroutineScope {
      if (channels.isEmpty()) {
         ConsoleLogger.logWarning("No channel specified in channelData.json!")
         return@coroutineScope
      }
      channels.forEach {
         ConsoleLogger.logInfo("Joined Channel ${it.userData.displayName}")
         twitchClient.chat.joinChannel(it.userData.name)
      }
      ConsoleLogger.logInfo("Successfully joined all Twitch Channels.")
   }
   private fun createClient(): TwitchClient {
      ConsoleLogger.logInfo("Start creating TwitchClient...")
      val client = TwitchClientBuilder.builder()
         .withChatAccount(OAuth2Credential("twitch", System.getenv("TOKEN")))
         .withEnableChat(true)
         .withEnableHelix(true)
         .build()
      ConsoleLogger.logInfo("TwitchClient successfully created.")
      return client
   }
   fun getChannel(name: String): User? = twitchClient.helix.getUsers(System.getenv("TOKEN"), null, listOf(name)).execute().users.firstOrNull()
   fun getMods(channelID: String): List<Moderator?>? = twitchClient.helix.getModerators(System.getenv("TOKEN"), channelID, null, null, null).execute().moderators
}