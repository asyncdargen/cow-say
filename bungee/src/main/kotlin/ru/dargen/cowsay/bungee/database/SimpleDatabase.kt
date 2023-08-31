package ru.dargen.cowsay.bungee.database

import org.intellij.lang.annotations.Language
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class SimpleDatabase(url: String, user: String, password: String) {

    val executor = Executors.newSingleThreadExecutor()
    val connection = DriverManager.getConnection(url, user, password)

    fun executeUpdate(@Language("SQL") query: String, vararg parameters: Any?) = CompletableFuture.runAsync({
        connection.prepareStatement(query, *parameters).use(PreparedStatement::executeUpdate)
    }, executor)

    fun executeQuery(@Language("SQL") query: String, vararg parameters: Any?) = CompletableFuture.supplyAsync({
        connection.prepareStatement(query, *parameters).use(PreparedStatement::executeQuery)
    }, executor)

    fun execute(@Language("SQL") query: String, vararg parameters: Any?) = CompletableFuture.supplyAsync({
        connection.prepareStatement(query, *parameters)
            .apply(PreparedStatement::execute)
            .resultSet
    }, executor)

    //Утилитарная функция для создания заполненного стейтмента
    private fun Connection.prepareStatement(query: String, vararg parameters: Any?) = prepareStatement(query)
        .apply { parameters.forEachIndexed { index, any -> setObject(index + 1, any) } }

}