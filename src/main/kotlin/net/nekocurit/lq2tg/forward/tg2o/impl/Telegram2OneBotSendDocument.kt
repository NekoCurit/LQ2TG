package net.nekocurit.lq2tg.forward.tg2o.impl

import dev.inmo.tgbotapi.extensions.api.get.getFileAdditionalInfo
import dev.inmo.tgbotapi.types.message.content.DocumentContent
import dev.inmo.tgbotapi.types.message.content.MessageContent
import net.nekocurit.lq2tg.data.enums.EnumEntityType
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction
import net.nekocurit.lq2tg.utils.TelegramBotUtils.saveFile
import java.io.File

class Telegram2OneBotSendDocument: Telegram2OneBotSend {
    override suspend fun handle(action: Telegram2OneBotSendAction, baseContent: MessageContent) {
        baseContent
            .let { it as? DocumentContent }
            ?.also { content ->
                val filename = content.media.fileName ?: return@also
                val file = File(File(File(action.task.config.oneBot.localFile.localDir), "upload"), filename)
                action.telegramBot.saveFile(action.telegramBot.getFileAdditionalInfo(content.media.fileId), file)

                val filePath = action.task.config.oneBot.localFile.applyToWrapper(file)

                when (action.entitySendType) {
                    EnumEntityType.GROUP -> action.oneBot.uploadGroupFileAsync(action.entitySendId.toLong(), filePath, file.name)
                    EnumEntityType.PRIVATE -> action.oneBot.uploadPrivateFileAsync(action.entitySendId.toLong(), filePath, file.name)
                }

                action.messageId = -1
                action.description = "[文件] ${file.absolutePath}"
            }
    }
}