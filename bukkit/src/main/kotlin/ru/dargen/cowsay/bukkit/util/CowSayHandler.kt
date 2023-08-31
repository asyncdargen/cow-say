package ru.dargen.cowsay.bukkit.util

import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import ru.dargen.cowsay.bukkit.Plugin
import ru.dargen.cowsay.bukkit.cow.CowSayService

object CowSayHandler : PluginMessageListener {

    init {
        Bukkit.getMessenger().registerIncomingPluginChannel(Plugin, "dargen:cowsay", this)
    }

    @Suppress("UnstableApiUsage")
    override fun onPluginMessageReceived(channel: String, receiver: Player, bytes: ByteArray) {
        if (channel != "dargen:cowsay") return

        val (name, data) = ByteStreams.newDataInput(bytes).run { readUTF() to (readInt() to readUTF()) }
        val player = Bukkit.getPlayer(name) ?: return

        CowSayService.process(player, data)
    }

}