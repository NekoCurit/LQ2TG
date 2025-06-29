package net.nekocurit.lq2tg.forward.tg2o.impl

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import dev.inmo.tgbotapi.extensions.api.files.downloadFile
import dev.inmo.tgbotapi.extensions.api.get.getFileAdditionalInfo
import dev.inmo.tgbotapi.types.message.content.MessageContent
import dev.inmo.tgbotapi.types.message.content.PhotoContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction
import java.util.*

class Telegram2OneBotSendImage: Telegram2OneBotSend {
    override suspend fun handle(action: Telegram2OneBotSendAction, baseContent: MessageContent) {
        baseContent
            .let { it as? PhotoContent }
            ?.also { content ->
                val imageData = action.telegramBot.downloadFile(action.telegramBot.getFileAdditionalInfo(content.media.fileId))

                action.sendMessageAndSetDescription(mutableListOf<ArrayMessage>().apply {
                    add(ArrayMessage(SegmentType.image, ArrayMessage.Data(
                        file = "base64://${Base64.getEncoder().encodeToString(imageData)}"
                    )))
                    content.text?.also { text ->
                        add(ArrayMessage(SegmentType.text,ArrayMessage.Data(text = text)))
                    }
                }, "[图片] ${content.text ?: ""}")
            }
    }
}