package net.nekocurit.lq2tg.forward.o2tg.impl

import cn.rtast.rob.annotations.OneBot11CompatibilityApi
import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import dev.inmo.tgbotapi.extensions.api.send.media.sendDocument
import dev.inmo.tgbotapi.extensions.api.send.sendActionUploadDocument
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayAction
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayParse

open class OneBot2TelegramArrayParseFile: OneBot2TelegramArrayParse {

    open val segmentType = SegmentType.file

    @OptIn(OneBot11CompatibilityApi::class)
    override suspend fun parse(action: OneBot2TelegramArrayAction, message: ArrayMessage) {
        if (message.type == segmentType) {
            // getFile 是耗时操作
            action.beUploading()
            val url = action.oneBot.getFile(message.data.fileId!!).url
            action.beUploading()

            var isLocalFile = false
            val file = when {
                url.startsWith("http") -> InputFile.fromUrl(url)
                else -> InputFile.fromFile(action.task.config.oneBot.localFile.apply(url))
                    .also {
                        isLocalFile = true
                    }
            }

            action.upload(url, file)

            if (isLocalFile && action.task.config.oneBot.localFile.autoDelete) action.task.config.oneBot.localFile.apply(url).delete()
        }
    }

    open suspend fun OneBot2TelegramArrayAction.upload(url: String, file: InputFile) {
        cancelTextMessage(
            telegramBot.sendDocument(
                chatId = sendChatId,
                threadId = sendThread,
                document = file
            ),
            description = "[文件] $url"
        )
    }

    open suspend fun OneBot2TelegramArrayAction.beUploading() {
        telegramBot.sendActionUploadDocument(
            chatId = sendChatId,
            threadId = sendThread
        )
    }
}