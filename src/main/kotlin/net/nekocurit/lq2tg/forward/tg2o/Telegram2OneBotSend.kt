package net.nekocurit.lq2tg.forward.tg2o

import cn.rtast.rob.onebot.OneBotAction
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.message.content.MessageContent

interface Telegram2OneBotSend {
    suspend fun handle(action: OneBotAction, bot: TelegramBot, userId: String, baseContent: MessageContent): Telegram2OneBotSendResult?
}