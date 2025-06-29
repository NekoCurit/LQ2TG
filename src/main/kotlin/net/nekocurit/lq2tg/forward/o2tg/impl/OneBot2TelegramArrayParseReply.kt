package net.nekocurit.lq2tg.forward.o2tg.impl

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGMessageIdExtensions.getMessageLink
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayAction
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayParse

class OneBot2TelegramArrayParseReply: OneBot2TelegramArrayParse {
    override fun parse(action: OneBot2TelegramArrayAction, message: ArrayMessage) {
        if (message.type == SegmentType.reply) {
            action.system.databaseManager
                .getMessageLink(
                    entityPlatform = action.entityPlatform,
                    entityFromType = action.entityFromType,
                    entityFromId = action.entityFromId,
                    entityMessageId = message.data.id ?: ""
                )
                ?.also { data ->
                    action.replyMessageId = data.id
                }
        }
    }
}