package net.nekocurit.lq2tg.forward.listener

import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.onebot.OneBotListener
import dev.inmo.tgbotapi.bot.exceptions.CommonRequestException
import dev.inmo.tgbotapi.extensions.api.chat.forum.createForumTopic
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageThreadId
import dev.inmo.tgbotapi.types.RawChatId
import dev.inmo.tgbotapi.utils.RGBColor
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGTopicExtensions.deleteTopic
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGTopicExtensions.getTopicOrCreate
import net.nekocurit.lq2tg.forward.LQ2TGForward

class ForwardOneBotListener(val task: LQ2TGForward): OneBotListener {

    override suspend fun onPrivateMessage(message: PrivateMessage) {
        handlePrivateMessage(message, true)
    }

    suspend fun handlePrivateMessage(message: PrivateMessage, allowRetry: Boolean) {
        runCatching {
            task.system.databaseManager
                .getTopicOrCreate(task.name, message.userId.toString()) {
                    task.telegramBot.createForumTopic(
                        chatId = ChatId(RawChatId(task.config.telegram.groupId)),
                        name = message.userId.toString(),
                        color = RGBColor(0xFF0000)
                    ).messageThreadId.long
                }
                .also { topicId ->
                    val description = message.rawMessage

                    task.telegramBot.sendMessage(
                        chatId = ChatId(RawChatId(task.config.telegram.groupId)),
                        text = message.rawMessage,
                        threadId = MessageThreadId(topicId)
                    )

                    task.system.logger.info("[${task.name}] [接收消息] [${message.userId}] $description")
                }
        }
            .onFailure { e ->
                when (e) {
                    is CommonRequestException -> {
                        val exceptionMessage = e.message ?: return@onFailure

                        // Topic 不存在
                        // 删除链接并尝试重新创建
                        if (exceptionMessage.contains("Bad Request: message thread not found")) {
                            task.system.databaseManager.deleteTopic(task.name, message.userId.toString())
                            if (allowRetry) handlePrivateMessage(message = message, allowRetry = false)
                        }
                    }
                }
            }
    }

}