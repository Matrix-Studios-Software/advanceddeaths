package ltd.matrixstudios.deaths.deaths

import com.google.common.io.Files
import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.deaths.AdvancedDeaths
import java.io.File
import java.lang.reflect.Type
import java.util.*

object DeathConfig
{

    val CONFIG_TYPE: Type = object : TypeToken<MutableMap<UUID, MutableList<DeathEntry>>>() {}.type
    val file = File(AdvancedDeaths.instance.dataFolder, "deaths.json")

    fun loadDeaths()
    {
        if (file.exists())
        {
            val reader = file.reader(Charsets.UTF_8)

            AdvancedDeaths.instance.GSON.fromJson<
                    MutableMap<UUID, MutableList<DeathEntry>>>(
                reader, CONFIG_TYPE
            ).forEach {
                DeathHandler.set(it.key, it.value)
            }
        } else {
            file.createNewFile()

            file.writeText(
                AdvancedDeaths.instance.GSON.toJson
                    (DeathHandler.items,
                    CONFIG_TYPE
                ), Charsets.UTF_8
            )
        }
    }

    fun createItem(item: DeathEntry)
    {
        item.loadItems()

        val list = DeathHandler.items.getOrDefault(item.owner, mutableListOf())
            .sortedBy{ it.at }
            .toMutableList()

        if (list.size >= 15) {
            list.removeFirst()
        }

        list.add(item)

        DeathHandler.set(item.owner, list)

        if (file.exists()) {
            file.writeText(
                AdvancedDeaths.instance.GSON.toJson(DeathHandler.items, CONFIG_TYPE),
                Charsets.UTF_8
            )
        }
    }

    fun saveItem(item: DeathEntry) {
        val list = DeathHandler.items.getOrDefault(item.owner, mutableListOf())

        list.removeIf { it.uniqueId == item.uniqueId }
        list.add(item)

        DeathHandler.set(item.owner, list)

        if (file.exists()) {
            file.writeText(
                AdvancedDeaths.instance.GSON.toJson(DeathHandler.items, CONFIG_TYPE),
                Charsets.UTF_8
            )
        }
    }

    fun deleteItem(item: DeathEntry)
    {
        DeathHandler.items.remove(item.owner)


        if (file.exists())
        {
            Files.write(
                AdvancedDeaths.instance.GSON.toJson
                    (DeathHandler.items,
                    CONFIG_TYPE
                ), file,
                Charsets.UTF_8
            )
        }
    }
}