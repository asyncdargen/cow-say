package ru.dargen.cowsay.bukkit.cow

import org.bukkit.Location
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCow
import org.bukkit.entity.Player
import ru.dargen.cowsay.bukkit.util.CowSayConfig

data class CowSayData(val player: Player, val cow: CraftCow, var ticksAlive: Int = 0, var angle: Float = 0f) {

    val isFinished get() = !player.isOnline || ticksAlive >= CowSayConfig.Cow.AliveTicks

    fun incrementTicksAndPrepareLocation(): Location {
        ticksAlive++

        //Получаем локацию под нужным углом на нужном радиусе относительно игрока
        val location = player.location.add(
            player.location
                .apply { yaw = angle; pitch = 0f }
                .direction
                .normalize()
                .multiply(CowSayConfig.Cow.Radius)
        )
        //Задаем взгляд в точку куда корова идет
        location.direction = location.clone().subtract(cow.location).toVector().normalize()

        angle += CowSayConfig.Cow.AngularSpeed
        return location
    }

}
