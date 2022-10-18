package de.jakkoble.utils

object ConsoleLogger {
   fun logInfo(message: String) {
      println("$TEXT_WHITE[${TEXT_GREEN}INFO$TEXT_WHITE] $message$TEXT_RESET")
   }
   fun logWarning(message: String) {
      println("$TEXT_WHITE[${TEXT_YELLOW}WARNING$TEXT_WHITE] $message$TEXT_RESET")
   }
}

const val TEXT_RESET = "\u001B[0m"
const val TEXT_GREEN = "\u001B[32m"
const val TEXT_YELLOW = "\u001B[33m"
const val TEXT_WHITE = "\u001B[37m"