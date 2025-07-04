package net.nekocurit.lq2tg.forward.o2tg.impl

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayAction
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayParse

class OneBot2TelegramArrayParseImage: OneBot2TelegramArrayParse {
    override suspend fun parse(action: OneBot2TelegramArrayAction, message: ArrayMessage) {
        if (message.type == SegmentType.image) {
            action.sendTextMessageBuilder.append("${message.data.summary?.takeIf { it.isNotEmpty() } ?: "[图片]"} ${message.data.url}")
        }
    }
}