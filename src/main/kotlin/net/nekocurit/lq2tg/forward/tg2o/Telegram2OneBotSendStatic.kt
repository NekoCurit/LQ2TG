package net.nekocurit.lq2tg.forward.tg2o

import net.nekocurit.lq2tg.forward.tg2o.impl.Telegram2OneBotSendSticker
import net.nekocurit.lq2tg.forward.tg2o.impl.Telegram2OneBotSendText

object Telegram2OneBotSendStatic {
    val sends = arrayOf(
        Telegram2OneBotSendText(),
        Telegram2OneBotSendSticker(),
    )
}