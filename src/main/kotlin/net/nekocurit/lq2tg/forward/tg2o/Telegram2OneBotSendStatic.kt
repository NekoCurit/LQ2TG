package net.nekocurit.lq2tg.forward.tg2o

import net.nekocurit.lq2tg.forward.tg2o.impl.*

object Telegram2OneBotSendStatic {
    val sends = arrayOf(
        Telegram2OneBotSendText(),
        Telegram2OneBotSendSticker(),
        Telegram2OneBotSendImage(),
        Telegram2OneBotSendVoice(),
        Telegram2OneBotSendLocation(),
        Telegram2OneBotSendDocument()
    )
}