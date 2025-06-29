package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable

/**
 * @param state 是否启用代理
 * @param type 可选 socks/http
 * @param host 代理地址
 * @param port 代理端口
 */
@Serializable
class DataConfigForwardTelegramProxy(
    val state: Boolean = false,
    val type: String = "socks",
    val host: String = "localhost",
    val port: Int = 8080
)