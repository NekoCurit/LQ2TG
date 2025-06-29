package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable

/**
 * @param botToken Telegram 机器人 token
 * @param groupId 要转发到的群组 id 需要开启话题 (Topics) 功能
 */
@Serializable
class DataConfigForwardTelegram(
    val botToken: String = "",
    val proxy: DataConfigForwardTelegramProxy = DataConfigForwardTelegramProxy(),
    val groupId: Long = 0L
)