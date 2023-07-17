package ltd.matrixstudios.deaths.deaths

import ltd.matrixstudios.deaths.serialize.ItemStackAdapter
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

    @Transient
    val itemArray = ItemStackAdapter.itemStackArrayFromBase64(inventory)

    @Transient
    val armorArray = ItemStackAdapter.itemStackArrayFromBase64(armorContents)
}