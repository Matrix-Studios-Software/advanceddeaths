package ltd.matrixstudios.deaths.deaths

import java.util.*

object DeathHandler {

    val items = mutableMapOf<UUID, MutableList<DeathEntry>>()

    fun loadEntriesToMap() {
        DeathConfig.loadDeaths()
    }

    fun getById(id: UUID) : MutableList<DeathEntry> {
        return items.computeIfAbsent(id) { return@computeIfAbsent mutableListOf<DeathEntry>() }
    }
}