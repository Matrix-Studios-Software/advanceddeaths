package ltd.matrixstudios.deaths.deaths

import java.util.*
import java.util.concurrent.CompletableFuture

object DeathHandler {

    val items = mutableMapOf<UUID, MutableList<DeathEntry>>()

    fun loadEntriesToMap() {
        DeathConfig.loadDeaths()
    }

    fun set(id: UUID, items: MutableList<DeathEntry>) {
        CompletableFuture.runAsync {
            for (item in items) {
                item.loadItems()
            }

            this.items[id] = items
        }
     }

    fun getDeathsOrderedByDate(id: UUID) : MutableList<DeathEntry> {
        return getById(id)
            .sortedBy { System.currentTimeMillis().minus(it.at) / 1000L }
            .toMutableList()
    }

    fun getById(id: UUID) : MutableList<DeathEntry> {
        return items.computeIfAbsent(id) { return@computeIfAbsent mutableListOf<DeathEntry>() }
    }
}