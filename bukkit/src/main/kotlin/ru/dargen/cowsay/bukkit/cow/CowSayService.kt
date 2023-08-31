package ru.dargen.cowsay.bukkit.cow

import net.minecraft.server.v1_12_R1.PacketPlayOutExplosion
import net.minecraft.server.v1_12_R1.Vec3D
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCow
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import ru.dargen.cowsay.bukkit.Plugin

object CowSayService {

    val runningCows = mutableSetOf<CowSayData>()

    init {
        Bukkit.getScheduler().runTaskTimer(Plugin, this::run, 1, 1)
    }

    private fun run() {
        runningCows.removeIf {
            val location = it.incrementTicksAndPrepareLocation()

            it.player.spawnParticle(Particle.FLAME, location, 1, .0, .0, .0, .0)
            it.cow.teleport(location)

            if (it.ticksAlive % 4 == 0) {
                it.player.playSound(location, Sound.ENTITY_COW_AMBIENT, 1f, 1f)
            }

            //взрыв и удаление
            if (it.isFinished) {
                it.cow.remove()

                (it.player as CraftPlayer)
                    .handle
                    .playerConnection
                    .sendPacket(location.run { PacketPlayOutExplosion(x, y, z, 3f, emptyList(), Vec3D.a) })
                it.player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f)
            }

            it.isFinished
        }
    }

    fun process(player: Player, data: Pair<Int, String>) {
        val cow = player.location.spawnCow(data)
        val cowData = CowSayData(player, cow)

        runningCows.add(cowData)
    }

    private fun Location.spawnCow(data: Pair<Int, String>): CraftCow {
        val cow = world.spawnEntity(this, EntityType.COW) as CraftCow

        cow.setAI(false)
        cow.isInvulnerable = true
        cow.isSilent = true

        cow.isCustomNameVisible = true
        cow.customName = "${data.first}: ${data.second}"

        return cow
    }

}