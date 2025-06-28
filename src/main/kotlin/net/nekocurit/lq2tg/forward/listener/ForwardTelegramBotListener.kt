package net.nekocurit.lq2tg.forward.listener

import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContextReceiver
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onContentMessage
import dev.inmo.tgbotapi.utils.extensions.threadIdOrNull
import net.nekocurit.lq2tg.database.extensions.DataManagerQ2TGTopicExtensions.getUserId
import net.nekocurit.lq2tg.forward.LQ2TGForward
import net.nekocurit.lq2tg.forward.tg2o.Telegram2OneBotSendStatic

class ForwardTelegramBotListener(val task: LQ2TGForward): BehaviourContextReceiver<Unit> {

    override suspend fun invoke(content: BehaviourContext) {
        content.onContentMessage { message ->
            if (message.chat.id.chatId.long == task.config.telegram.groupId) {
                val topicId = message.threadIdOrNull?.long ?: return@onContentMessage

                task.system.databaseManager.getUserId(task.name, topicId)
                    ?.also { userId ->
                        runCatching {
                            Telegram2OneBotSendStatic.sends
                                .firstNotNullOfOrNull { send ->
                                    send.handle(task.oneBot, this.bot, userId, message.content)
                                }
                                ?.also { result ->
                                    task.system.logger.info("[${task.name}] [发送消息] [${userId}] ${result.description}")
                                }
                        }
                            .onFailure { e ->
                                task.system.logger.warn("[${task.name}] [发送消息] [${userId}] 失败", e)
                            }

                    }
            }
        }
    }

}