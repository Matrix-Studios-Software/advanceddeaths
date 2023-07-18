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
import ltd.matrixstudios.receive.utils.Chat
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

@CommandAlias("ad|advanceddeaths|deathhistory|deaths")
@CommandPermission("advanceddeaths.use")
class DeathsCommand : BaseCommand() {

    @HelpCommand
    fun help(player: Player) {
        player.sendMessage(Chat.format("&6=== &eShowing help for &6/deaths ==="))
        player.sendMessage(Chat.format("&edeaths check &6[player] - Opens the death history menu"))
        player.sendMessage(Chat.format("&edeaths reset &6[player] - Resets the death of a player"))
        player.sendMessage(Chat.format("&edeaths rawoutput &6[player] - Shows death history without a menu"))
        player.sendMessage(Chat.format("&edeaths manualrefund &6<player> <position> - Manually refunds a player's inventory at a certain position"))
        player.sendMessage(Chat.format("&edeaths giveto <player> - Gives your current inventory to a selected player"))
        player.sendMessage(Chat.format("&edeaths takefrom <player> - Gives a selected player's inventory to you"))
    }

    @Subcommand("check")
    @CommandCompletion("@players")
    fun check(player: Player, @Name("target") @Flags("other") offlinePlayer: OfflinePlayer) {
        DeathsMenu(player, offlinePlayer).updateMenu()
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