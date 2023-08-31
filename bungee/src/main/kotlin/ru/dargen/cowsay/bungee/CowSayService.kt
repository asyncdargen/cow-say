package ru.dargen.cowsay.bungee

import com.google.common.io.ByteStreams
import net.md_5.bungee.api.connection.ProxiedPlayer
import ru.dargen.cowsay.bungee.database.CowSayDatabase
import java.util.logging.Level

object CowSayService {

    init {
        CowSayDatabase
        Proxy.registerChannel("dargen:cowsay")
    }

    fun requestCow(player: ProxiedPlayer, text: String) {
        CowSayDatabase.update(player.name, text).whenComplete { (count, text), throwable ->
            throwable?.let {
                player.sendMessage("§cЧто-то пошло не так!")
                Logger.log(Level.SEVERE, "Error while updating saycow for ${player.name}", it)
                return@whenComplete
            }

            player.requestCowSpigot(count, text)
        }
    }

    @Suppress("UnstableApiUsage")
    private fun ProxiedPlayer.requestCowSpigot(count: Int, text: String) {
        val data = ByteStreams.newDataOutput().apply {
            writeUTF(name)
            writeInt(count)
            writeUTF(text)
        }.toByteArray()

        server.info.sendData("dargen:cowsay", data)

        sendMessage("§aЗапрос отправлен №$count: $text")
    }

}