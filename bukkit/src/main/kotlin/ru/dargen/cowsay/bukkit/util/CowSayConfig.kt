package ru.dargen.cowsay.bukkit.util

import org.bukkit.configuration.Configuration
import ru.dargen.cowsay.bukkit.Plugin
import kotlin.math.PI

object CowSayConfig {

    private val RawConfig: Configuration

    object Cow {

        private val Section = RawConfig.getConfigurationSection("cow")

        val AliveTicks = Section.getInt("alive-ticks")
        val Radius = Section.getDouble("radius")
        private val LinearSpeed = Section.getDouble("speed")

        //Преобразуем линейную скорость в блоках в угловую под наш радиус
        val AngularSpeed = (360 * (LinearSpeed / (2 * PI * Radius))).toFloat()

    }

    init {
        Plugin.saveDefaultConfig()
        Plugin.reloadConfig()

        RawConfig = Plugin.config
    }

}