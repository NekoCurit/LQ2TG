package net.nekocurit.lq2tg.database.data

import org.jetbrains.exposed.sql.Table

object DataBaseQ2TGMessageId : Table("q2tg_message_id") {
    /**
     * 目标平台的消息 Id
     * Telegram 平台的 Id 更加稳定
     */
    val id = long("id")
    /**
     * 用于区分多账号
     */
    val entityPlatform = text("entity_platform")
    /**
     * 原始平台的消息来源类型 (好友/群)
     */
    val entityFromType = integer("entity_from_type")
    /**
     * 原始平台的消息来源 Id (好友号/群号)
     */
    val entityFromId = text("entity_from_id")
    /**
     * 原始平台的消息 Id
     */
    val entityMessageId = text("entity_message_id")

    override val primaryKey = PrimaryKey(id)
}
