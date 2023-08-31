package ru.dargen.cowsay.bungee.util

import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import ru.dargen.cowsay.bungee.Plugin

object CowSayConfig {

    private val Yaml = ConfigurationProvider.getProvider(YamlConfiguration::class.java)

    private val File = Plugin.dataFolder.resolve("config.yml")
    private val RawConfig: Configuration

    object Database {

        private val Section = RawConfig.getSection("database")

        val Url = Section.getString("url")
        val User = Section.getString("user")
        val Password = Section.getString("password")

    }

    init {
        if (!File.exists()) {
            File.parentFile?.mkdirs()

            Yaml.save(Yaml.load(Plugin.getResourceAsStream("bungee_config.yml")), File)
        }

        RawConfig = Yaml.load(File)
    }

}