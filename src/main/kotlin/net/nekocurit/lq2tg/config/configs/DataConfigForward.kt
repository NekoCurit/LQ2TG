package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable

@Serializable
data class DataConfigForward(
    val name: String = "",
    val oneBot: DataConfigForwardOneBot = DataConfigForwardOneBot(),
    val telegram: DataConfigForwardTelegram = DataConfigForwardTelegram(),
)