package net.nekocurit.lq2tg.data

import net.nekocurit.lq2tg.data.enums.EnumEntityType

data class DataMessageLink(
    val id: Long,
    val entityPlatform: String,
    val entityFromType: EnumEntityType,
    val entityFromId: String,
    val entityMessageId: String
)
