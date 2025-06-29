package net.nekocurit.lq2tg.forward.tg2o.impl

import cn.rtast.rob.enums.SegmentType
import dev.inmo.tgbotapi.types.message.content.MessageContent
import dev.inmo.tgbotapi.types.message.content.StickerContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction
import net.nekocurit.lq2tg.utils.Telegram2OneBotSendActionUtils.sendMediaAndDescription

class Telegram2OneBotSendSticker: Telegram2OneBotSend {
    override suspend fun handle(action: Telegram2OneBotSendAction, baseContent: MessageContent) {
        baseContent
            .let { it as? StickerContent }
            ?.also { content ->
                action.sendMediaAndDescription(SegmentType.image, content, "[贴纸]")
            }
    }
}