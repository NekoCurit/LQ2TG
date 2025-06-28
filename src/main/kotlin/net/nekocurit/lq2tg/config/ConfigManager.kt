package net.nekocurit.lq2tg.config

import com.charleskorn.kaml.Yaml
import net.nekocurit.lq2tg.config.configs.DataConfigRoot
import net.nekocurit.lq2tg.LQ2TG
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Paths

class ConfigManager(val system: LQ2TG) {

    companion object {
        val CONFIG_FILE: File = Paths.get("config.yml").toFile()
    }

    lateinit var config: DataConfigRoot

    fun readConfig() {
        runCatching {
            if (CONFIG_FILE.exists()) {
                config = Yaml.default.decodeFromString(
                    deserializer = DataConfigRoot.serializer(),
                    string = CONFIG_FILE.readText(
                        charset = StandardCharsets.UTF_8
                    )
                )
            } else {
                config = DataConfigRoot()
                CONFIG_FILE.writeText(
                    text = Yaml.default.encodeToString(
                        serializer = DataConfigRoot.serializer(),
                        value = config
                    ),
                    charset = StandardCharsets.UTF_8
                )
                system.logger.info("生成默认配置")
            }
        }
            .onSuccess {
                system.logger.info("加载配置成功")
            }
            .onFailure { e ->
                system.logger.error("加载配置失败", e)
                throw e
            }
    }
}
