package ru.dargen.cowsay.bungee.database

import ru.dargen.cowsay.bungee.util.CowSayConfig
import java.util.concurrent.CompletableFuture

typealias CowSayData = Pair<Int, String>

object CowSayDatabase {

    private val Database = SimpleDatabase(
        CowSayConfig.Database.Url,
        CowSayConfig.Database.User,
        CowSayConfig.Database.Password
    )

    init {
        Database.executeUpdate(
            """
            CREATE TABLE IF NOT EXISTS `cowsay` (
                `username` VARCHAR(16) NOT NULL,
                `lastSay` TEXT NOT NULL COLLATE 'utf8mb4_general_ci',
                `sayCount` INT NOT NULL, 
                PRIMARY KEY(`username`)
            )
            """.trimIndent()
        ).get()
    }

    //Вставняем данные текста и кол-во вводов или обновляем существующие и возвращаем их
    fun update(player: String, text: String): CompletableFuture<CowSayData> = Database.execute("""
        INSERT INTO `cowsay` VALUES (?, ?, 1) 
            ON DUPLICATE KEY UPDATE `lastSay` = ?, `sayCount` = `sayCount` + 1
            RETURNING `lastSay` as `text`, `sayCount` as `count`
    """.trimIndent(), player, text, text).thenApply { rs ->
        rs.use {
            require(it.next()) { "Insert operation failed" }

            it.getInt("count") to it.getString("text")
        }
    }

}