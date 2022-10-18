package de.jakkoble.modules.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object DataManager {
   private const val filePath = "channelData.json"
   init {
      val file = File(filePath)
      if (!file.exists()) {
         file.createNewFile()
         file.writeText(Json.encodeToString(channels))
      }
   }
   fun load() {
      val data = Json.decodeFromString<MutableList<ChannelData>?>(File(filePath).readText())
      channels.addAll(data ?: return)
   }
   fun updateChannelData() {

   }
}