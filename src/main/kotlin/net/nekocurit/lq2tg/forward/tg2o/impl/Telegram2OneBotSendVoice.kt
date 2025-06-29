package net.nekocurit.lq2tg.forward.tg2o.impl

import cn.rtast.rob.enums.SegmentType
import dev.inmo.tgbotapi.types.message.content.MessageContent
import dev.inmo.tgbotapi.types.message.content.VoiceContent
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSend
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction
import net.nekocurit.lq2tg.utils.Telegram2OneBotSendActionUtils.sendMediaAndDescription

class Telegram2OneBotSendVoice: Telegram2OneBotSend {
    override suspend fun handle(action: Telegram2OneBotSendAction, baseContent: MessageContent) {
        baseContent
            .let { it as? VoiceContent }
            ?.also { content ->
                // 实际上 Telegram 语音是可以添加备注的 不过那个..应该不会有人用吧 (就用了 OneBot 那里也不支持)
                action.sendMediaAndDescription(SegmentType.record, content, "[语音]")
            }
    }
}