package net.nekocurit.lq2tg.forward.o2tg

import cn.rtast.rob.event.raw.message.ArrayMessage
import cn.rtast.rob.onebot.OneBotAction

interface OneBot2TelegramArrayParse {
    fun parse(action: OneBotAction, message: ArrayMessage): String?
}