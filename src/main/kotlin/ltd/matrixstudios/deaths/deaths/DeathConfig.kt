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

    fun loadDeaths()
    {
        val file = File(AdvancedDeaths.instance.dataFolder, "deaths.json")

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

    fun saveItem(item: DeathEntry)
    {
        val file = File(AdvancedDeaths.instance.dataFolder, "deaths.json")

        item.loadItems()

        val list = DeathHandler.items.getOrDefault(item.owner, mutableListOf())
            .sortedByDescending { it.at }
            .toMutableList()

        val toDelete = mutableListOf<DeathEntry>()

        if (list.size >= 15) {
            toDelete.add(list.removeFirst())
            list.add(item)

            DeathHandler.items[item.owner] = list
        } else {
            list.add(item)
            DeathHandler.items[item.owner] = list
        }


        if (file.exists()) {
            file.writeText(
                AdvancedDeaths.instance.GSON.toJson(DeathHandler.items, CONFIG_TYPE),
                Charsets.UTF_8
            )
        }

        for (delete in toDelete) {
            deleteItem(delete)
        }
    }

    fun deleteItem(item: DeathEntry)
    {
        val file = File(AdvancedDeaths.instance.dataFolder, "reclaims.json")


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