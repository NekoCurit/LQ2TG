package net.nekocurit.lq2tg.forward.listener

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.message.ArrayMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContextReceiver
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onContentMessage
import dev.inmo.tgbotapi.utils.extensions.threadIdOrNull
import net.nekocurit.lq2tg.data.enums.EnumEntityType
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGMessageIdExtensions.createMessageLink
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGMessageIdExtensions.getMessageLink
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGTopicExtensions.getFromTopicId
import net.nekocurit.lq2tg.forward.LQ2TGForward
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendAction
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendStatic

class ForwardTelegramBotListener(val task: LQ2TGForward): BehaviourContextReceiver<Unit> {

    override suspend fun invoke(content: BehaviourContext) {
        content.onContentMessage { message ->
            if (message.chat.id.chatId.long == task.config.telegram.groupId) {
                val topicId = message.threadIdOrNull?.long ?: return@onContentMessage

                task.system.databaseManager.getFromTopicId(task.name, topicId)
                    ?.also { data ->
                        runCatching {
                            val action = Telegram2OneBotSendAction(
                                oneBot = task.oneBot,
                                telegramBot = this.bot,
                                system = task.system,
                                entitySendType = EnumEntityType.PRIVATE,
                                entitySendId = data.userId,
                                prefix = message.replyTo
                                    ?.let { task.system.databaseManager.getMessageLink(it.messageId.long) }
                                    ?.let { ArrayMessage(SegmentType.reply, ArrayMessage.Data(id = it.entityMessageId))}
                            )

                            Telegram2OneBotSendStatic.sends.any { send ->
                                action.messageId?.also { return@any true } // 提前结束

                                send.handle(action, message.content)
                                return@any false
                            }

                            action.messageId?.also { messageId ->
                                task.system.databaseManager.createMessageLink(
                                    id = message.messageId.long,
                                    entityPlatform = task.name,
                                    entityFromType = action.entitySendType,
                                    entityFromId = action.entitySendId,
                                    entityMessageId = messageId.toString()
                                )
                                task.system.logger.info("[${task.name}] [发送消息] [${data.userId}] ${action.description} (${messageId})")
                            }
                        }
                            .onFailure { e ->
                                task.system.logger.warn("[${task.name}] [发送消息] [${data.userId}] 失败", e)
                            }

                    }
            }
        }
    }

}