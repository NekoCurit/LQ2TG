package net.nekocurit.lq2tg.forward

import cn.rtast.rob.onebot.OneBotAction
import dev.inmo.tgbotapi.bot.TelegramBot
import net.nekocurit.lq2tg.LQ2TG
import net.nekocurit.lq2tg.config.configs.DataConfigForward

class LQ2TGForward(val system: LQ2TG, val config: DataConfigForward) {
    init {
        if (config.oneBot.url.isEmpty()) throw Exception("配置 onebot.url 不能为空")
        if (config.telegram.botToken.isEmpty()) throw Exception("配置 telegram.botToken 不能为空")
        if (config.telegram.groupId == 0L) throw Exception("配置 telegram.groupId 不能为空")
    }

    val name = config.name

    lateinit var oneBot: OneBotAction
    lateinit var telegramBot: TelegramBot
}