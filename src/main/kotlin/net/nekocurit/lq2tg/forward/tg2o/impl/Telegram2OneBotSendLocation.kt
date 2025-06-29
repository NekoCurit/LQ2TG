package net.nekocurit.lq2tg.forward.tg2o.impl

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import dev.inmo.tgbotapi.types.message.content.LocationContent
import dev.inmo.tgbotapi.types.message.content.MessageContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction

class Telegram2OneBotSendLocation: Telegram2OneBotSend {
    override suspend fun handle(action: Telegram2OneBotSendAction, baseContent: MessageContent) {
        baseContent
            .let { it as? LocationContent }
            ?.also { content ->
                action.sendMessageAndSetDescription(mutableListOf<ArrayMessage>().apply {
                    add(ArrayMessage(SegmentType.location, ArrayMessage.Data(
                        lat = content.location.latitude,
                        lon = content.location.longitude
                    )))
                }, "[位置信息] 经度:${content.location.longitude} 纬度:${content.location.latitude}")
            }
    }
}