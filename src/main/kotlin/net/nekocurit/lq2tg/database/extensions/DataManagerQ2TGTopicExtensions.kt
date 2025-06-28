package net.nekocurit.lq2tg.database.extensions

import kotlinx.coroutines.runBlocking
import net.nekocurit.lq2tg.data.DataTopicInfo
import net.nekocurit.lq2tg.database.DataBaseManager
import net.nekocurit.lq2tg.database.data.DataBaseQ2TGTopic
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object DataManagerQ2TGTopicExtensions {

    /**
     * 获取或创建新的 TopicId 链接
     *
     * @param name 用于区分多个原始平台 bot 账号
     * @param userId 原始平台用户 Id
     * @param title Topic 标题信息 (由于 Telegram 无 api 获取需要本地保存)
     * @param create 如果不存在则调用创建
     */
    fun DataBaseManager.getTopicOrCreate(name: String, userId: String, title: String, create: suspend () -> Long) = transaction(database) {
        DataBaseQ2TGTopic
            .selectAll()
            .where {
                (DataBaseQ2TGTopic.name eq name) and (DataBaseQ2TGTopic.userId eq userId)
            }
            .singleOrNull()
            ?.let {
                DataTopicInfo(
                    name = it[DataBaseQ2TGTopic.name],
                    userId = it[DataBaseQ2TGTopic.userId],
                    topicId = it[DataBaseQ2TGTopic.topicId],
                    title = it[DataBaseQ2TGTopic.title],
                )
            }
            ?: runBlocking { create.invoke() }
                .also { topicId ->
                    DataBaseQ2TGTopic.insert {
                        it[this.name] = name
                        it[this.userId] = userId
                        it[this.topicId] = topicId
                        it[this.title] = title
                    }
                }
                .let { topicId ->
                    DataTopicInfo(
                        name = name,
                        userId = userId,
                        topicId = topicId,
                        title = title,
                    )
                }
    }

    /**
     * 从 TopicId 获取原始平台用户 Id
     *
     * @param name 用于区分多个原始平台 bot 账号
     * @param topicId Telegram 平台的 TopicId
     */
    fun DataBaseManager.getFromTopicId(name: String, topicId: Long) = transaction(database) {
        DataBaseQ2TGTopic
            .selectAll()
            .where {
                (DataBaseQ2TGTopic.name eq name) and (DataBaseQ2TGTopic.topicId eq topicId)
            }
            .singleOrNull()
            ?.let {
                DataTopicInfo(
                    name = it[DataBaseQ2TGTopic.name],
                    userId = it[DataBaseQ2TGTopic.userId],
                    topicId = it[DataBaseQ2TGTopic.topicId],
                    title = it[DataBaseQ2TGTopic.title],
                )
            }
    }

    /**
     * 删除已存储的 TopicId 链接
     *
     * @param name 用于区分多个原始平台 bot 账号
     * @param userId 原始平台用户 Id
     */
    fun DataBaseManager.deleteTopic(name: String, userId: String) {
        transaction(database) {
            DataBaseQ2TGTopic
                .deleteWhere {
                    (DataBaseQ2TGTopic.name eq name) and (DataBaseQ2TGTopic.userId eq userId)
                }
        }
    }

    /**
     * 更新 TopicId 数据
     *
     * @param name 用于区分多个原始平台 bot 账号
     * @param userId 原始平台用户 Id
     * @param title 是否更新标题数据 不留空则更新
     */
    fun DataBaseManager.updateTopic(name: String, userId: String, title: String?) {
        transaction(database) {
            DataBaseQ2TGTopic
                .update({  (DataBaseQ2TGTopic.name eq name) and (DataBaseQ2TGTopic.userId eq userId) }) {
                    title?.also { update ->
                        it[this.title] = update
                    }
                }
        }
    }
}