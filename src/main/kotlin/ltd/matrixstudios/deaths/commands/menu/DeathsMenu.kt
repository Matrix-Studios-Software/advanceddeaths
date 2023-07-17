package ltd.matrixstudios.deaths.commands.menu

import ltd.matrixstudios.deaths.deaths.DeathEntry
import ltd.matrixstudios.deaths.deaths.DeathHandler
import ltd.matrixstudios.deaths.utils.menu.Button
import ltd.matrixstudios.deaths.utils.menu.pagination.PaginatedMenu
import ltd.matrixstudios.receive.utils.Chat
import org.apache.commons.lang.StringUtils
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class DeathsMenu(val player: Player, val target: OfflinePlayer) : PaginatedMenu(36, player) {

    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var index = 0
        for (entry in DeathHandler.getById(target.uniqueId)) {
            buttons[index++] = DeathButton(target, entry, index)
        }

        return buttons
    }

    override fun getButtonPositions(): List<Int> {
        return listOf(
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
        )
    }

    override fun getHeaderItems(player: Player): MutableMap<Int, Button> {
        return mutableMapOf(
            1 to Button.placeholder(),
            2 to Button.placeholder(),
            3 to Button.placeholder(),
            4 to Button.placeholder(),
            5 to Button.placeholder(),
            6 to Button.placeholder(),
            7 to Button.placeholder(),
            17 to Button.placeholder(),
            18 to Button.placeholder(),
            26 to Button.placeholder(),
            35 to Button.placeholder(),
            36 to Button.placeholder(),
            37 to Button.placeholder(),
            38 to Button.placeholder(),
            39 to Button.placeholder(),
            40 to Button.placeholder(),
            41 to Button.placeholder(),
            42 to Button.placeholder(),
            43 to Button.placeholder(),
            44 to Button.placeholder(),
            9 to Button.placeholder(),
            27 to Button.placeholder(),
        )
    }


    override fun getButtonsPerPage(): Int {
        return 21
    }

    override fun getTitle(player: Player): String {
        return "Deaths of " + target.name
    }

    class DeathButton(val target: OfflinePlayer, val deathEntry: DeathEntry, val position: Int) : Button() {
        override fun getMaterial(player: Player): Material {
            return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&7&m${StringUtils.repeat("-", 30)}"))
            desc.add(Chat.format("&eDead Player: &f" + target.name))
            desc.add(Chat.format("&eArmor Contents: &f" + deathEntry.armorArray.count { it != null }))
            desc.add(Chat.format("&eInventory Contents: &f" + deathEntry.itemArray.count { it != null }))
            desc.add(Chat.format("&eDeath Message: &f" + deathEntry.diedTo))
            desc.add(Chat.format("&7&m${StringUtils.repeat("-", 30)}"))
            return desc
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format("&e${target.name} Death #${position}")
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }
}