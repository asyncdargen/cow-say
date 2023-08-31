package ru.dargen.cowsay.bungee

import net.md_5.bungee.api.plugin.Plugin
import ru.dargen.cowsay.bungee.command.CowSayCommand
import ru.dargen.cowsay.bungee.util.CowSayConfig

lateinit var Plugin: CowSayPlugin

val Proxy get() = Plugin.proxy
val Logger get() = Plugin.logger

class CowSayPlugin : Plugin() {

    override fun onEnable() {
        Plugin = this

        CowSayConfig
        CowSayService

        proxy.pluginManager.registerCommand(this, CowSayCommand)
    }

}