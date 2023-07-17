package ltd.matrixstudios.receive.utils

import org.bukkit.ChatColor

object Chat {

    fun format(string: String) : String
    {
        return ChatColor.translateAlternateColorCodes('&', string)
    }
}