package ltd.matrixstudios.deaths.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Flags
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import ltd.matrixstudios.deaths.commands.menu.DeathsMenu
import ltd.matrixstudios.deaths.deaths.DeathConfig
import ltd.matrixstudios.deaths.deaths.DeathHandler
import ltd.matrixstudios.deaths.utils.TimeUtils
import ltd.matrixstudios.receive.utils.Chat
import org.apache.commons.lang.StringUtils
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.Date

@CommandAlias("ad|advanceddeaths|deathhistory|deaths")
@CommandPermission("advanceddeaths.use")
class DeathsCommand : BaseCommand() {

    @HelpCommand
    fun help(player: Player) {
        player.sendMessage(Chat.format("&6=== &eShowing help for &6/deaths ==="))
        player.sendMessage(Chat.format("&edeaths check &6[player] - Opens the death history menu"))
        player.sendMessage(Chat.format("&edeaths rawoutput &6[player] - Shows death history without a menu"))
        player.sendMessage(Chat.format("&edeaths manualrefund &6<player> <position> - Manually refunds a player's inventory at a certain position"))
        player.sendMessage(Chat.format("&edeaths giveto &6<player> - Gives your current inventory to a selected player"))
        player.sendMessage(Chat.format("&edeaths takefrom &6<player> - Gives a selected player's inventory to you"))
    }

    @Subcommand("check")
    @CommandCompletion("@players")
    fun check(player: Player, @Name("target") @Flags("other") offlinePlayer: OfflinePlayer) {
        DeathsMenu(player, offlinePlayer).updateMenu()
    }

    @Subcommand("rawoutput")
    @CommandCompletion("@players")
    fun rawOutput(player: CommandSender, @Name("target") @Flags("other") target: Player) {
        val deaths = DeathHandler.getDeathsOrderedByDate(target.uniqueId).take(10)

        if (deaths.isEmpty()) {
            player.sendMessage(Chat.format("&cThis player has no deaths"))
            return
        }
        player.sendMessage(Chat.format("&e&lDeaths of &f" + target.displayName))
        player.sendMessage(Chat.format("&7&m${StringUtils.repeat("-", 40)}"))
        for (death in deaths) {
            player.sendMessage(Chat.format("&e[${TimeUtils.formatIntoCalendarString(Date(death.at))}] " + death.diedTo + " [Refunded: " + (if (death.refunded) "Yes" else "No") + "] " + "At " + death.world + " (" + death.x + ", " + death.y + ", " + death.z + ")"))
        }
        player.sendMessage(Chat.format("&7&m${StringUtils.repeat("-", 40)}"))
    }

    @Subcommand("manualrefund")
    @CommandCompletion("@players")
    fun manualRefund(player: CommandSender, @Name("target") @Flags("other") target: Player, @Name("position") pos: Int) {
        val deaths = DeathHandler.getDeathsOrderedByDate(target.uniqueId)

        if (deaths.size < pos) {
            player.sendMessage(Chat.format("&cThis player does not have enough deaths to do this!"))
            return
        }

        val at = deaths[pos-1]

        if (at.refunded) {
            player.sendMessage(Chat.format("&cThis death has already been refunded!"))
            return
        }

        val bukkitPlayer = target.player
        bukkitPlayer.inventory.clear()
        bukkitPlayer.inventory.contents = at.itemArray
        bukkitPlayer.inventory.armorContents = at.armorArray
        bukkitPlayer.updateInventory()
        player.sendMessage(Chat.format("&eYou have refunded " + bukkitPlayer.displayName + "&e's inventory"))

        at.refunded = true
        DeathConfig.saveItem(at)
    }

    @Subcommand("giveto")
    @CommandCompletion("@players")
    fun giveTo(player: Player, @Name("target") @Flags("other") target: Player) {
        val currInv = player.inventory.contents
        val currArmor = player.inventory.armorContents

        target.inventory.clear()
        target.inventory.contents = currInv
        target.inventory.armorContents = currArmor
        target.updateInventory()

        target.sendMessage(Chat.format("&eYou have been given " + player.displayName + "&e's inventory and armor!"))
        player.sendMessage(Chat.format("&eYou gave your armor and inventory to " + target.displayName))
    }

    @Subcommand("takefrom")
    @CommandCompletion("@players")
    fun takeFrom(player: Player, @Name("target") @Flags("other") target: Player) {
        val currInv = target.inventory.contents
        val currArmor = target.inventory.armorContents

        player.inventory.clear()
        player.inventory.contents = currInv
        player.inventory.armorContents = currArmor
        player.updateInventory()

        player.sendMessage(Chat.format("&eYou have taken the armor and inventory from " + target.displayName))
    }

}