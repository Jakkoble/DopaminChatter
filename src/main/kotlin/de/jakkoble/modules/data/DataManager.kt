package de.jakkoble.modules.data

import de.jakkoble.utils.ConsoleLogger
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object DataManager {
   const val filePath = "data"
   private val json = Json { prettyPrint = true }
   init {
      val file = File(filePath)
      if (!file.exists()) {
         ConsoleLogger.logWarning("Data Folder does not exist!")
         file.mkdir()
         ConsoleLogger.logInfo("Data Folder created.")
         val channelFile = File("$filePath/205919808.json")
         if (!channelFile.exists()) channelFile.createNewFile()
         val channelData = ChannelData(UserData("jakkoble", "Jakkoble","205919808"))
         channelFile.writeText(json.encodeToString(channelData))
      }
   }
   fun load() {
      ConsoleLogger.logInfo("Loading Channel Data...")
      File(filePath).listFiles()?.forEach {
         val data = Json.decodeFromString<ChannelData?>(it.readText())
         if (data != null) channels.add(data)
      }
      ConsoleLogger.logInfo("Successfully loaded all Channel Data.")
   }
   fun updateChannelData() {
      channels.forEach {
         val file = File("$filePath/${it.userData.id}.json")
         if (!file.exists()) file.createNewFile()
         file.writeText(json.encodeToString(it))
      }
      ConsoleLogger.logInfo("Updated all Channel Data")
   }
}
fun ChannelData.update(newData: ChannelData) {
   channels.removeIf { it.userData.id == userData.id }
   channels.add(newData)
   DataManager.updateChannelData()
}