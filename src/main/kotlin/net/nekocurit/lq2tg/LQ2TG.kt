package net.nekocurit.lq2tg

import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.LogLevel
import kotlinx.coroutines.awaitCancellation
import net.nekocurit.lq2tg.config.ConfigManager
import net.nekocurit.lq2tg.database.DataBaseManager
import net.nekocurit.lq2tg.forward.ForwardManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LQ2TG {
    val logger: Logger = LoggerFactory.getLogger("LQ2TG")

    val configManager = ConfigManager(this)
    val databaseManager = DataBaseManager(this)
    val forwardManager = ForwardManager(this)

    suspend fun start() {
        // 用于修复 dev.inmo:tgbotapi 招笑 Job was cancel 异常
        KSLog.default = KSLog { level: LogLevel, tag: String?, message: Any, throwable: Throwable? -> }

        configManager.readConfig()
        databaseManager.connect()

        runCatching {
            forwardManager.createForward(configManager.config.forward)
        }
            .onFailure { e ->
                logger.error("启动失败", e)
            }

        awaitCancellation()
    }

}