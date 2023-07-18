package ltd.matrixstudios.deaths.deaths

import ltd.matrixstudios.deaths.serialize.ItemStackAdapter
import org.bukkit.inventory.ItemStack
import java.util.UUID

data class DeathEntry(
    val uniqueId: UUID,
    val owner: UUID,
    val inventory: String,
    val armorContents: String,
    val at: Long,
    val world: String,
    val x: Int,
    val y: Int,
    val z: Int,
    var diedTo: String,
    var refunded: Boolean
) {

    @Transient
    lateinit var itemArray: Array<ItemStack?>

    @Transient
    lateinit var armorArray: Array<ItemStack?>

    fun loadItems() {
        itemArray = ItemStackAdapter.itemStackArrayFromBase64(inventory)
        armorArray = ItemStackAdapter.itemStackArrayFromBase64(armorContents)
    }
}