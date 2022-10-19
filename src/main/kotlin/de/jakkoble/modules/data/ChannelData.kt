package de.jakkoble.modules.data

import kotlinx.serialization.Serializable

val channels: MutableList<ChannelData> = mutableListOf()
@Serializable
data class ChannelData(
   val userData: UserData,
   val enabled: Boolean = true,
   val customEmotes: MutableList<String> = mutableListOf(),
   val writingChance: Int = 50
)
@Serializable
data class UserData(val name: String, val id: String)