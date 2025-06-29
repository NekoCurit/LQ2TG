package net.nekocurit.lq2tg.forward.tg2o

import net.nekocurit.lq2tg.forward.tg2o.impl.Telegram2OneBotSendImage
import net.nekocurit.lq2tg.forward.tg2o.impl.Telegram2OneBotSendLocation
import net.nekocurit.lq2tg.forward.tg2o.impl.Telegram2OneBotSendSticker
import net.nekocurit.lq2tg.forward.tg2o.impl.Telegram2OneBotSendText
import net.nekocurit.lq2tg.forward.tg2o.impl.Telegram2OneBotSendVoice

object Telegram2OneBotSendStatic {
    val sends = arrayOf(
        Telegram2OneBotSendText(),
        Telegram2OneBotSendSticker(),
        Telegram2OneBotSendImage(),
        Telegram2OneBotSendVoice(),
        Telegram2OneBotSendLocation()
    )
}