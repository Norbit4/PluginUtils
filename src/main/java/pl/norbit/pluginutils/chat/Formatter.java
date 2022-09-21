package pl.norbit.pluginutils.chat;

import org.bukkit.ChatColor;

public class Formatter {

    public static String format(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
