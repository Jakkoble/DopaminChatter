package de.jakkoble.modules.data

import kotlinx.serialization.Serializable

val channels: MutableList<ChannelData> = mutableListOf()
@Serializable
data class ChannelData(
   val userData: UserData,
   var enabled: Boolean = true,
   var customEmotes: MutableList<String> = mutableListOf(),
   var writingChance: Int = 50
)
fun getChannelDataByID(channelID: String) = channels.firstOrNull { it.userData.id == channelID }
@Serializable
data class UserData(val name: String, val displayName: String, val id: String)