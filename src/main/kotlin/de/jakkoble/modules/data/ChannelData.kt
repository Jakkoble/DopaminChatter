package de.jakkoble.modules.data

val channels: MutableList<ChannelData> = mutableListOf()
data class ChannelData(
   val channelID: String,
   val channelName: String,
   val enabled: Boolean = true,
   val customEmotes: MutableList<String> = mutableListOf(),
   val writingChance: Int = 50
)