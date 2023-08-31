package ru.dargen.cowsay.bungee.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import ru.dargen.cowsay.bungee.CowSayService

object CowSayCommand : Command("cowsay") {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (sender !is ProxiedPlayer) sender.sendMessage("§cЭта команда только для игроков!")
        else {
            val text = args.getOrNull(0) ?: run {
                sender.sendMessage("§cУкажите текст!")
                return
            }

            CowSayService.requestCow(sender, text)
        }
    }

}