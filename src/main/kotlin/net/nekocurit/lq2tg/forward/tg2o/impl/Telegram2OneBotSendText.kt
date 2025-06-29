package net.nekocurit.lq2tg.forward.tg2o.impl

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import dev.inmo.tgbotapi.types.message.content.MessageContent
import dev.inmo.tgbotapi.types.message.content.TextContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction

class Telegram2OneBotSendText: Telegram2OneBotSend {
    override suspend fun handle(action: Telegram2OneBotSendAction, baseContent: MessageContent) {
        baseContent
            .let { it as? TextContent }
            ?.also { content ->
                action.sendMessageAndSetDescription(mutableListOf<ArrayMessage>().apply {
                    add(ArrayMessage(SegmentType.text, ArrayMessage.Data(text = content.text)))
                }, content.text)
            }
    }
}