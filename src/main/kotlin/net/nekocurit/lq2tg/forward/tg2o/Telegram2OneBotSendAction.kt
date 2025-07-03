package net.nekocurit.lq2tg.forward.tg2o

import cn.rtast.rob.event.raw.message.ArrayMessage
import cn.rtast.rob.onebot.OneBotAction
import dev.inmo.tgbotapi.bot.TelegramBot
import net.nekocurit.lq2tg.LQ2TG
import net.nekocurit.lq2tg.data.enums.EnumEntityType
import net.nekocurit.lq2tg.forward.LQ2TGForward

class Telegram2OneBotSendAction(
    val task: LQ2TGForward,
    val oneBot: OneBotAction,
    val telegramBot: TelegramBot,
    val system: LQ2TG,
    val entitySendType: EnumEntityType,
    val entitySendId: String,
    val prefix: ArrayMessage?,
) {
    var messageId: Long? = null
    var description: String? = null

    suspend fun sendMessageAndSetDescription(message: MutableList<ArrayMessage>, description: String) {
        prefix?.also { add -> message.add(add) }
        messageId = when (entitySendType) {
            EnumEntityType.PRIVATE -> oneBot.sendPrivateMessage(entitySendId.toLong(), message)
            EnumEntityType.GROUP -> oneBot.sendGroupMessage(entitySendId.toLong(), message)
        }
        this.description = description
    }
}