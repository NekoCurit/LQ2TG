package net.nekocurit.lq2tg.database

import net.nekocurit.lq2tg.LQ2TG
import net.nekocurit.lq2tg.database.data.DataBaseQ2TGTopic
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DataBaseManager(val base: LQ2TG) {
    lateinit var database: Database

    fun connect() {
        database = Database.connect(
            url = base.configManager.config.database.url,
            user = base.configManager.config.database.user,
            driver = base.configManager.config.database.driver,
            password = base.configManager.config.database.password
        )

        transaction(database) {
            SchemaUtils.create(DataBaseQ2TGTopic)
        }

        base.logger.info("连接数据路成功")
    }

}