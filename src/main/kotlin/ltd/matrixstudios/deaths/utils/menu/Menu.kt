package ltd.matrixstudios.deaths.utils.menu

import ltd.matrixstudios.deaths.utils.menu.buttons.PlaceholderButton
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.lang.Math.ceil
import java.util.concurrent.CompletableFuture

abstract class Menu(
    private val player: Player
) {

    var staticSize: Int? = null
    var placeholder: Boolean = false
    var stealable: Boolean = false

    abstract fun getButtons(player: Player) : MutableMap<Int, Button>
    abstract fun getTitle(player: Player) : String


    fun getAllButtons() : MutableMap<Int, Button> {
        return getButtons(player)
    }
    open fun size(buttons: Map<Int, Button>): Int {
        var highest = 0
        for (buttonValue in buttons.keys) {
            if (buttonValue > highest) {
                highest = buttonValue
            }
        }
        return (kotlin.math.ceil((highest + 1) / 9.0) * 9.0).toInt()
    }


    //dont need to use update menu because it is just placing items in menu. If it gets to it ill do it
    fun openMenu() {
        var finalSize = size(getButtons(player))

        if (staticSize != null)
        {
            finalSize = staticSize!!
        }

        val inventory = Bukkit.createInventory(null, finalSize, getTitle(player))
        val buttons = getAllButtons()

        if (player.openInventory.topInventory != null)
        {
            player.closeInventory()
        }

        if (MenuController.paginatedMenuMap.containsKey(player.uniqueId))
        {
            MenuController.paginatedMenuMap.remove(player.uniqueId)
        }

        MenuController.addToMenuMap(player, this)

        if (placeholder) {
            val placeholder = PlaceholderButton(Material.STAINED_GLASS_PANE, mutableListOf(), "", 7)

            for (index in 0 until staticSize!!) {
                if (buttons[index] == null) {
                    buttons[index] = placeholder
                    inventory.setItem(index, placeholder.constructItemStack(player))
                }
            }
        }

        CompletableFuture.runAsync {
            for (entry in buttons)
            {
                inventory.setItem(entry.key, entry.value.constructItemStack(player))
            }
        }


        player.openInventory(inventory)
    }





}