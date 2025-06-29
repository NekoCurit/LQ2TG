package net.nekocurit.lq2tg.utils

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import dev.inmo.tgbotapi.extensions.api.files.downloadFile
import dev.inmo.tgbotapi.extensions.api.get.getFileAdditionalInfo
import dev.inmo.tgbotapi.types.message.content.MediaContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction
import java.util.Base64

object Telegram2OneBotSendActionUtils {
    suspend fun Telegram2OneBotSendAction.sendMediaAndDescription(segmentType: SegmentType, content: MediaContent, description: String) {
        val data = telegramBot.downloadFile(telegramBot.getFileAdditionalInfo(content.media.fileId))

        sendMessageAndSetDescription(mutableListOf<ArrayMessage>().apply {
            add(ArrayMessage(segmentType,ArrayMessage.Data(
                file = "base64://${Base64.getEncoder().encodeToString(data)}"
            )))
        }, description)
    }
}