package ltd.matrixstudios.deaths

import co.aikar.commands.PaperCommandManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import ltd.matrixstudios.deaths.commands.DeathsCommand
import ltd.matrixstudios.deaths.deaths.DeathHandler
import ltd.matrixstudios.deaths.listener.DeathListener
import ltd.matrixstudios.deaths.utils.menu.listener.MenuListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class AdvancedDeaths : JavaPlugin()
{
    companion object
    {
        lateinit var instance: AdvancedDeaths
    }

    val GSON: Gson = GsonBuilder()
        .serializeNulls()
        .setLongSerializationPolicy(
            LongSerializationPolicy.STRING
        )
        .setPrettyPrinting()
        .create()

    override fun onEnable()
    {
        instance = this
        saveDefaultConfig()

        server.pluginManager.registerEvents(MenuListener(), this)
        server.pluginManager.registerEvents(DeathListener(), this)

        DeathHandler.loadEntriesToMap()

        val startCommands = System.currentTimeMillis()
        loadCommands()

        Bukkit.getLogger().log(
            Level.INFO,
            "[commands] Commands took ${System.currentTimeMillis().minus(startCommands)}ms to register"
        )
    }

    fun loadCommands()
    {
        PaperCommandManager(this).apply {
            this.registerCommand(DeathsCommand())
        }
    }
}