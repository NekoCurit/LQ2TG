package net.nekocurit.lq2tg.utils

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.files.downloadFileStream
import dev.inmo.tgbotapi.types.files.PathedFile
import io.ktor.utils.io.jvm.javaio.copyTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object TelegramBotUtils {
    suspend fun TelegramBot.saveFile(pFile: PathedFile, file: File) {
        file.parentFile.mkdirs()
        val channel = downloadFileStream(pFile)

        withContext(Dispatchers.IO) {
            file.outputStream().use { opt ->
                channel.copyTo(opt)
            }
        }
    }
}