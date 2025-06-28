package net.nekocurit.lq2tg.forward.o2tg.impl

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import cn.rtast.rob.onebot.OneBotAction
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayParse

class OneBot2TelegramArrayParseImage: OneBot2TelegramArrayParse {
    override fun parse(action: OneBotAction, message: ArrayMessage) = message
        .takeIf { it.type == SegmentType.image }
        ?.data
        ?.let { "${it.summary ?: "[图片]"} ${it.url}"}
}