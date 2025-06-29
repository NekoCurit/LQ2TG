package net.nekocurit.lq2tg.forward.listener

import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.onebot.OneBotListener
import dev.inmo.tgbotapi.bot.exceptions.CommonRequestException
import dev.inmo.tgbotapi.extensions.api.chat.forum.createForumTopic
import dev.inmo.tgbotapi.extensions.api.chat.forum.editForumTopic
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageThreadId
import dev.inmo.tgbotapi.types.RawChatId
import dev.inmo.tgbotapi.utils.RGBColor
import net.nekocurit.lq2tg.data.enums.EnumEntityType
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGMessageIdExtensions.createMessageLink
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGTopicExtensions.deleteTopic
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGTopicExtensions.getTopicOrCreate
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGTopicExtensions.updateTopic
import net.nekocurit.lq2tg.forward.LQ2TGForward
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayAction
import net.nekocurit.lq2tg.forward.o2tg.OneBot2TelegramArrayStatic

class ForwardOneBotListener(val task: LQ2TGForward): OneBotListener {

    override suspend fun onPrivateMessage(message: PrivateMessage) {
        handlePrivateMessage(message, true)
    }

    suspend fun handlePrivateMessage(message: PrivateMessage, allowRetry: Boolean) {
        runCatching {
            val newTitle = "${message.sender.nickname} (${message.sender.userId})"
            task.system.databaseManager
                .getTopicOrCreate(task.name, message.userId.toString(), newTitle) {
                    task.telegramBot.createForumTopic(
                        chatId = ChatId(RawChatId(task.config.telegram.groupId)),
                        name = newTitle,
                        color = RGBColor(0xFF0000)
                    ).messageThreadId.long
                }
                .also { data ->
                    if (data.title != newTitle) {
                        task.telegramBot.editForumTopic(
                            chatId = ChatId(RawChatId(task.config.telegram.groupId)),
                            messageThreadId = MessageThreadId(data.topicId),
                            name = newTitle
                        )
                        task.system.databaseManager.updateTopic(
                            name = task.name,
                            userId = message.userId.toString(),
                            title = newTitle
                        )
                    }

                    val action = OneBot2TelegramArrayAction(
                        task = task,
                        oneBot = task.oneBot,
                        telegramBot = task.telegramBot,
                        system = task.system,
                        entityPlatform = task.name,
                        entityFromType = EnumEntityType.PRIVATE,
                        entityFromId = message.userId.toString(),
                        entityMessageId = message.messageId.toString(),
                        sendChatId = ChatId(RawChatId(task.config.telegram.groupId)),
                        sendThread = MessageThreadId(data.topicId),
                    )

                    message.message.any { array ->
                        OneBot2TelegramArrayStatic.parses.forEach { parser ->
                            parser.parse(action, array)
                        }
                        return@any action.otherMessage != null
                    }

                    action.getOtherMessageOrSendText()
                        .also { sentMessage ->
                            task.system.databaseManager.createMessageLink(
                                id = sentMessage.messageId.long,
                                entityPlatform = task.name,
                                entityFromType = EnumEntityType.PRIVATE,
                                entityFromId = message.userId.toString(),
                                entityMessageId = message.messageId.toString()
                            )
                            task.system.logger.info("[${task.name}] [接收消息] [${message.userId}] ${action.description}")
                        }
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