package net.nekocurit.lq2tg.database.data

import org.jetbrains.exposed.sql.Table
import java.util.UUID

object DataBaseQ2TGTopic : Table("q2tg_topic") {
    val id = uuid("id").clientDefault { UUID.randomUUID() }
    /**
     * 用于区分多账号
     */
    val name = text("name")
    /**
     * 原始平台的用户名
     */
    val userId = text("user_id")
    /**
     * 目标平台的 Topic Id
     */
    val topicId = long("topic_id")

    override val primaryKey = PrimaryKey(id)
}
