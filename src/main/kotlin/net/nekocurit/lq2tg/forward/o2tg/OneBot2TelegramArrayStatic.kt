package net.nekocurit.lq2tg.forward.o2tg

import net.nekocurit.lq2tg.forward.o2tg.impl.OneBot2TelegramArrayParseImage
import net.nekocurit.lq2tg.forward.o2tg.impl.OneBot2TelegramArrayParseReply
import net.nekocurit.lq2tg.forward.o2tg.impl.OneBot2TelegramArrayParseText

object OneBot2TelegramArrayStatic {
    val parses = arrayOf(
        OneBot2TelegramArrayParseReply(),
        OneBot2TelegramArrayParseText(),
        OneBot2TelegramArrayParseImage()
    )
}