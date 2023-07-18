package ltd.matrixstudios.deaths.listener

import ltd.matrixstudios.deaths.deaths.DeathConfig
import ltd.matrixstudios.deaths.deaths.DeathEntry
import ltd.matrixstudios.deaths.deaths.DeathHandler
import ltd.matrixstudios.deaths.serialize.ItemStackAdapter
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.*

class DeathListener : Listener {

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val deathMessage = event.deathMessage

        if (player !is Player) return

        val playerInventory = ItemStackAdapter.itemStackArrayToBase64(player.inventory.contents) ?: return
        val playerArmorContent = ItemStackAdapter.itemStackArrayToBase64(player.inventory.armorContents) ?: return

        val deathEntry = DeathEntry(
            UUID.randomUUID(),
            player.uniqueId,
            playerInventory,
            playerArmorContent,
            System.currentTimeMillis(),
            player.location.world.name,
            player.location.blockX, player.location.blockY, player.location.blockZ,
            deathMessage,
            false
        )

        DeathConfig.createItem(deathEntry)
    }
}