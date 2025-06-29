package net.nekocurit.lq2tg.database.extensions

import net.nekocurit.lq2tg.data.DataMessageLink
import net.nekocurit.lq2tg.data.enums.EnumEntityType
import net.nekocurit.lq2tg.database.DataBaseManager
import net.nekocurit.lq2tg.database.data.DataBaseQ2TGMessageId
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object DataManagerQ2TGMessageIdExtensions {

    /**
     * 获取消息链接信息
     *
     * @param entityPlatform 原始平台标记名
     * @param entityFromType 原始平台来源类型
     * @param entityFromId 原始平台来源 Id (群组为群号 私聊为目标账号)
     * @param entityMessageId 原始平台消息 Id
     */
    fun DataBaseManager.getMessageLink(entityPlatform: String, entityFromType: EnumEntityType, entityFromId: String, entityMessageId: String) = transaction(database) {
        DataBaseQ2TGMessageId
            .selectAll()
            .where {
                (DataBaseQ2TGMessageId.entityPlatform eq entityPlatform) and
                        (DataBaseQ2TGMessageId.entityFromType eq entityFromType.ordinal) and
                        (DataBaseQ2TGMessageId.entityFromId eq entityFromId) and
                        (DataBaseQ2TGMessageId.entityMessageId eq entityMessageId)
            }
            .singleOrNull()
            ?.let {
                DataMessageLink(
                    id = it[DataBaseQ2TGMessageId.id],
                    entityPlatform = it[DataBaseQ2TGMessageId.entityPlatform],
                    entityFromType = EnumEntityType.entries[it[DataBaseQ2TGMessageId.entityFromType]],
                    entityFromId = it[DataBaseQ2TGMessageId.entityFromId],
                    entityMessageId = it[DataBaseQ2TGMessageId.entityMessageId]
                )
            }
    }

    /**
     * 获取消息链接信息
     *
     * @param id 目标平台消息 Id
     */
    fun DataBaseManager.getMessageLink(id: Long) = transaction(database) {
        DataBaseQ2TGMessageId
            .selectAll()
            .where {
                DataBaseQ2TGMessageId.id eq id
            }
            .singleOrNull()
            ?.let {
                DataMessageLink(
                    id = it[DataBaseQ2TGMessageId.id],
                    entityPlatform = it[DataBaseQ2TGMessageId.entityPlatform],
                    entityFromType = EnumEntityType.entries[it[DataBaseQ2TGMessageId.entityFromType]],
                    entityFromId = it[DataBaseQ2TGMessageId.entityFromId],
                    entityMessageId = it[DataBaseQ2TGMessageId.entityMessageId]
                )
            }
    }

    /**
     * 创建消息链接限信息
     *
     * @param id 目标平台的消息 ID
     * @param entityPlatform 原始平台标记名
     * @param entityFromType 原始平台来源类型
     * @param entityFromId 原始平台来源 Id (群组为群号 私聊为目标账号)
     * @param entityMessageId 原始平台消息 Id
     */
    fun DataBaseManager.createMessageLink(id: Long, entityPlatform: String, entityFromType: EnumEntityType, entityFromId: String, entityMessageId: String) = transaction(database) {
        DataBaseQ2TGMessageId
            .insert {
                it[this.id] = id
                it[this.entityPlatform] = entityPlatform
                it[this.entityFromType] = entityFromType.ordinal
                it[this.entityFromId] = entityFromId
                it[this.entityMessageId] = entityMessageId
            }
            .let {
                DataMessageLink(
                    id = id,
                    entityPlatform = entityPlatform,
                    entityFromType = entityFromType,
                    entityFromId = entityFromId,
                    entityMessageId = entityMessageId
                )
            }
    }

    /**
     * 删除已存储的消息链接信息
     *
     * @param entityPlatform 原始平台标记名
     * @param entityFromType 原始平台来源类型
     * @param entityFromId 原始平台来源 Id (群组为群号 私聊为目标账号)
     * @param entityMessageId 原始平台消息 Id
     */
    fun DataBaseManager.deleteMessageLink(entityPlatform: String, entityFromType: EnumEntityType, entityFromId: String, entityMessageId: String) {
        transaction(database) {
            DataBaseQ2TGMessageId
                .deleteWhere {
                    (DataBaseQ2TGMessageId.entityPlatform eq entityPlatform) and
                            (DataBaseQ2TGMessageId.entityFromType eq entityFromType.ordinal) and
                            (DataBaseQ2TGMessageId.entityFromId eq entityFromId) and
                            (DataBaseQ2TGMessageId.entityMessageId eq entityMessageId)
                }
        }
    }

    /**
     * 删除已存储的消息链接信息
     *
     * @param id 目标平台消息 Id
     */
    fun DataBaseManager.deleteMessageLink(id: Long) {
        transaction(database) {
            DataBaseQ2TGMessageId
                .deleteWhere {
                    DataBaseQ2TGMessageId.id eq id
                }
        }
    }
}