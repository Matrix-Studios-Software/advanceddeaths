package ltd.matrixstudios.deaths.commands.menu

import ltd.matrixstudios.deaths.deaths.DeathEntry
import ltd.matrixstudios.deaths.utils.menu.Button
import ltd.matrixstudios.deaths.utils.menu.Menu
import ltd.matrixstudios.deaths.utils.menu.buttons.SimpleActionButton
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class DeathsDisplayItemsMenu(val entry: DeathEntry, val player: Player) : Menu(player) {

    init {
        staticSize = 45
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (item in entry.itemArray) {
            var button: Button? = null

            if (item != null) {
                button = DisplayItemStackButton(item)
            }

            if (button != null) {
                buttons[i++] = button
            } else i++
        }

        var i2 = 37
        for (armor in entry.armorArray) {
            var button: Button? = null

            if (armor != null) {
                button = DisplayItemStackButton(armor)
            }

            if (button != null) {
                buttons[i2++] = button
            } else i2++
        }

        buttons[45] = SimpleActionButton(Material.PAPER, mutableListOf(), "&eTeleport To Death", 0).setBody { player, i, clickType -> player.teleport(
            Location(Bukkit.getWorld(entry.world), entry.x.toDouble(), entry.y.toDouble(), entry.z.toDouble()))
        }

        return buttons
    }



    override fun getTitle(player: Player): String {
        return "Viewing Contents"
    }

    class DisplayItemStackButton(val itemStack: ItemStack) : Button() {

        override fun getButtonItem(player: Player): ItemStack? {
            return itemStack
        }
        override fun getMaterial(player: Player): Material {
           return Material.PAPER
        }

        override fun getDescription(player: Player): MutableList<String>? {
           return mutableListOf()
        }

        override fun getDisplayName(player: Player): String? {
            return "a"
        }

        override fun getData(player: Player): Short {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {

        }

    }
}