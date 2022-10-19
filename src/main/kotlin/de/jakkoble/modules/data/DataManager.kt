package de.jakkoble.modules.data

import de.jakkoble.utils.ConsoleLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object DataManager {
   const val filePath = "channelData.json"
   init {
      val file = File(filePath)
      if (!file.exists()) {
         ConsoleLogger.logWarning("channelData.json does not exist!")
         file.createNewFile()
         channels.add(ChannelData(UserData("Jakkoble", "205919808")))
         file.writeText(Json.encodeToString(channels))
         ConsoleLogger.logInfo("Created channelData.json file.")
      }
   }
   fun load() {
      ConsoleLogger.logInfo("Loading data from channelData.json...")
      val data = Json.decodeFromString<MutableList<ChannelData>?>(File(filePath).readText())
      channels.addAll(data ?: return)
      ConsoleLogger.logInfo("Successfully loaded all data from channelData.json.")
   }
   fun updateChannelData() {
      File(filePath).writeText(Json.encodeToString(channels))
      ConsoleLogger.logInfo("Updated data in channelData.json.")
   }
}