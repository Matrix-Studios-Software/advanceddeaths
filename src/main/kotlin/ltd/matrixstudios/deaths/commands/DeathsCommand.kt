package ltd.matrixstudios.deaths.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
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
    }

    @Subcommand("check")
    fun check(player: Player, @Name("target") @Flags("other") offlinePlayer: OfflinePlayer) {
        DeathsMenu(player, offlinePlayer).updateMenu()
    }

}