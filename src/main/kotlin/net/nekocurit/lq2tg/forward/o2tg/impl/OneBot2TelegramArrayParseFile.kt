package net.nekocurit.lq2tg.forward.o2tg.impl

import cn.rtast.rob.annotations.OneBot11CompatibilityApi
import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import dev.inmo.tgbotapi.extensions.api.send.media.sendDocument
import dev.inmo.tgbotapi.extensions.api.send.sendActionUploadDocument
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayAction
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayParse

class OneBot2TelegramArrayParseFile: OneBot2TelegramArrayParse {
    @OptIn(OneBot11CompatibilityApi::class)
    override suspend fun parse(action: OneBot2TelegramArrayAction, message: ArrayMessage) {
        if (message.type == SegmentType.file) {
            // getFile 是耗时操作
            action.beUploading()
            val url = action.oneBot.getFile(message.data.fileId!!).url
            action.beUploading()

            var isLocalFile = false

            action.cancelTextMessage(
                action.telegramBot.sendDocument(
                    chatId = action.sendChatId,
                    threadId = action.sendThread,
                    document = when {
                        url.startsWith("http") -> InputFile.fromUrl(url)
                        else -> InputFile.fromFile(action.task.config.oneBot.localFile.apply(url))
                            .also {
                                isLocalFile = true
                            }
                    }
                ),
                description = "[文件] $url"
            )

            if (isLocalFile && action.task.config.oneBot.localFile.autoDelete) action.task.config.oneBot.localFile.apply(url).delete()
        }
    }

    suspend fun OneBot2TelegramArrayAction.beUploading() {
        telegramBot.sendActionUploadDocument(
            chatId = sendChatId,
            threadId = sendThread
        )
    }
}