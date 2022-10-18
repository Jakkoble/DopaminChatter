package de.jakkoble.modules.data

import kotlinx.serialization.Serializable

val channels: MutableList<ChannelData> = mutableListOf()
@Serializable
data class ChannelData(
   val channelID: String,
   val channelName: String,
   val enabled: Boolean = true,
   val customEmotes: MutableList<String> = mutableListOf(),
   val writingChance: Int = 50
)