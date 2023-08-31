package ru.dargen.cowsay.bukkit

import org.bukkit.plugin.java.JavaPlugin
import ru.dargen.cowsay.bukkit.cow.CowSayService
import ru.dargen.cowsay.bukkit.util.CowSayConfig
import ru.dargen.cowsay.bukkit.util.CowSayHandler

lateinit var Plugin: CowSayPlugin

class CowSayPlugin : JavaPlugin() {

    override fun onEnable() {
        Plugin = this

        CowSayConfig
        CowSayService
        CowSayHandler
    }

}