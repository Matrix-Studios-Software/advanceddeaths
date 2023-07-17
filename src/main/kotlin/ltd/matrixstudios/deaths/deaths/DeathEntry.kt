package ltd.matrixstudios.deaths.deaths

import ltd.matrixstudios.deaths.serialize.ItemStackAdapter
import org.bukkit.inventory.ItemStack
import java.util.UUID

data class DeathEntry(
    val owner: UUID,
    val inventory: String,
    val armorContents: String,
    val at: Long,
    val x: Int,
    val y: Int,
    val z: Int,
    var diedTo: String
) {

    lateinit var itemArray: Array<ItemStack?>
    lateinit var armorArray: Array<ItemStack?>

    fun loadItems() {
        itemArray = ItemStackAdapter.itemStackArrayFromBase64(inventory)
        armorArray = ItemStackAdapter.itemStackArrayFromBase64(armorContents)
    }
}