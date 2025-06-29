package net.nekocurit.lq2tg.forward.o2tg

import cn.rtast.rob.onebot.OneBotAction
import net.nekocurit.lq2tg.LQ2TG
import net.nekocurit.lq2tg.data.enums.EnumEntityType

class OneBot2TelegramArrayAction(
    val action: OneBotAction,
    val system: LQ2TG,
    val entityPlatform: String,
    val entityFromType: EnumEntityType,
    val entityFromId: String,
    val entityMessageId: String
) {
    /**
     * 目标平台要回复的消息 Id
     * 设置为 0 则不回复
     */
    var replyMessageId = 0L
    /**
     * 要发送的文本消息
     */
    val sendTextMessageBuilder = StringBuilder()
}