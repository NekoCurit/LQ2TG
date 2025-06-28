package net.nekocurit.lq2tg.forward.tg2o.impl

import cn.rtast.rob.onebot.OneBotAction
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.types.message.content.MessageContent
import dev.inmo.tgbotapi.types.message.content.TextContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendResult

class Telegram2OneBotSendText: Telegram2OneBotSend {
    override suspend fun handle(action: OneBotAction, bot: TelegramBot, userId: String, baseContent: MessageContent) = baseContent
        .let { it as? TextContent }
        ?.let { content ->
            action.sendPrivateMessage(userId.toLong(), content.text)

            return@let Telegram2OneBotSendResult(content.text)
        }
}