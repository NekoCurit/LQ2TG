package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable

@Serializable
data class DataConfigDataBase(
    val url: String = "jdbc:sqlite:data.db",
    val driver: String = "org.sqlite.JDBC",
    val user: String = "",
    val password: String = ""
)