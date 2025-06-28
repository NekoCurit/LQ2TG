package net.nekocurit.lq2tg.forward

import cn.rtast.rob.OneBotFactory
import dev.inmo.tgbotapi.extensions.behaviour_builder.telegramBotWithBehaviourAndLongPolling
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import net.nekocurit.lq2tg.LQ2TG
import net.nekocurit.lq2tg.config.configs.DataConfigForward
import net.nekocurit.lq2tg.forward.listener.ForwardOneBotListener
import net.nekocurit.lq2tg.forward.listener.ForwardTelegramBotListener

class ForwardManager(val system: LQ2TG) {

    suspend fun createForward(config: DataConfigForward) {
        LQ2TGForward(system = system, config = config)
            .apply {
                oneBot = OneBotFactory.createClient(
                    address = config.oneBot.url,
                    accessToken = config.oneBot.token ?: "",
                    listener = ForwardOneBotListener(this)
                ).action
                telegramBot = telegramBotWithBehaviourAndLongPolling(
                    token = config.telegram.botToken,
                    scope = CoroutineScope(Dispatchers.IO),
                    block = ForwardTelegramBotListener(this)
                ).first
            }
    }

}