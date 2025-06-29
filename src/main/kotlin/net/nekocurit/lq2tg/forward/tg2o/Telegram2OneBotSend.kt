package net.nekocurit.lq2tg.forward.tg2o

import dev.inmo.tgbotapi.types.message.content.MessageContent

interface Telegram2OneBotSend {
    suspend fun handle(action: Telegram2OneBotSendAction, baseContent: MessageContent)
}