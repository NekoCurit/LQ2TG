package net.nekocurit.lq2tg.forward.o2tg

import cn.rtast.rob.event.raw.message.ArrayMessage

interface OneBot2TelegramArrayParse {
    suspend fun parse(action: OneBot2TelegramArrayAction, message: ArrayMessage)
}