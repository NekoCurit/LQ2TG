package net.nekocurit.lq2tg.forward.tg2o.impl

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import cn.rtast.rob.onebot.OneBotAction
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.files.downloadFile
import dev.inmo.tgbotapi.extensions.api.get.getFileAdditionalInfo
import dev.inmo.tgbotapi.types.message.content.MessageContent
import dev.inmo.tgbotapi.types.message.content.PhotoContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendResult
import java.util.*

class Telegram2OneBotSendImage: Telegram2OneBotSend {
    override suspend fun handle(action: OneBotAction, bot: TelegramBot, userId: String, baseContent: MessageContent) = baseContent
        .let { it as? PhotoContent }
        ?.let { content ->
            val imageData = bot.downloadFile(bot.getFileAdditionalInfo(content.media.fileId))

            action.sendPrivateMessage(
                userId.toLong(),
                mutableListOf<ArrayMessage>()
                    .apply {
                        add(ArrayMessage(SegmentType.image, ArrayMessage.Data(file = "base64://${Base64.getEncoder().encodeToString(imageData)}")))
                        content.text?.also { text ->
                            add(ArrayMessage(SegmentType.text,ArrayMessage.Data(text = text)))
                        }
                    }
            )

            return@let Telegram2OneBotSendResult("[图片] ${content.text ?: ""}")
        }
}