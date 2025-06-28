package net.nekocurit.lq2tg.forward.o2tg.impl

object OneBot2TelegramArrayStatic {
    val parses = arrayOf(
        OneBot2TelegramArrayParseText(),
        OneBot2TelegramArrayParseImage()
    )
}