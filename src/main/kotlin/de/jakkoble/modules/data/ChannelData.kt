package de.jakkoble.modules.data

import kotlinx.serialization.Serializable

val channels: MutableList<ChannelData> = mutableListOf()
@Serializable
data class ChannelData(
   val userData: UserData,
   var enabled: Boolean = true,
   val customEmotes: MutableList<String> = mutableListOf(),
   var writingChance: Int = 50
)
@Serializable
data class UserData(val name: String, val id: String)