package net.nekocurit.lq2tg.forward.o2tg

import cn.rtast.rob.onebot.OneBotAction
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatIdentifier
import dev.inmo.tgbotapi.types.MessageId
import dev.inmo.tgbotapi.types.MessageThreadId
import dev.inmo.tgbotapi.types.ReplyParameters
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import net.nekocurit.lq2tg.LQ2TG
import net.nekocurit.lq2tg.data.enums.EnumEntityType
import net.nekocurit.lq2tg.forward.LQ2TGForward

class OneBot2TelegramArrayAction(
    val task: LQ2TGForward,
    val oneBot: OneBotAction,
    val telegramBot: TelegramBot,
    val system: LQ2TG,
    val entityPlatform: String,
    val entityFromType: EnumEntityType,
    val entityFromId: String,
    val entityMessageId: String,
    val sendChatId: ChatIdentifier,
    val sendThread: MessageThreadId
) {
    /**
     * 目标平台要回复的消息 Id
     * 设置为 0 则不回复
     */
    var replyMessageId = 0L
    /**
     * 取消后续发送文本消息
     */
    var otherMessage: ContentMessage<*>? = null
        private set
    var description = ""
        private set

    fun cancelTextMessage(otherMessage: ContentMessage<*>, description: String) {
        this.otherMessage = otherMessage
        this.description = description
    }

    fun getReplyMessage() = replyMessageId
        .takeIf { it != 0L }
        ?.let { replyMessageId ->
            ReplyParameters(
                chatIdentifier = sendChatId,
                messageId = MessageId(replyMessageId),
                allowSendingWithoutReply = true
            )
        }

    /**
     * 要发送的文本消息
     */
    val sendTextMessageBuilder = StringBuilder()

    suspend fun getOtherMessageOrSendText() = otherMessage
        ?: let {
            description = sendTextMessageBuilder.toString()
            return@let telegramBot.sendMessage(
                chatId = sendChatId,
                threadId = sendThread,
                text = description,
                replyParameters = getReplyMessage()
            )
        }
}