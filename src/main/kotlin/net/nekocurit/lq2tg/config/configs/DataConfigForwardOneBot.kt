package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable

/**
 * @param url 连接地址 目前仅支持正向 WebSocket  例: ws://localhost:8080
 * @param token 连接token 可留空
 */
@Serializable
class DataConfigForwardOneBot(
    val url: String = "ws://localhost:8080",
    val token: String? = ""
)