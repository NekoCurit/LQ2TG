package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable

@Serializable
class DataConfigRoot(
    val forward: DataConfigForward = DataConfigForward(),
    val database: DataConfigDataBase = DataConfigDataBase()
)