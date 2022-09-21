package pl.norbit.pluginutils.register;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginUtilsRegistry {

    private static JavaPlugin plugin;

    public static void register(JavaPlugin javaPlugin){
        PluginUtilsRegistry.plugin = javaPlugin;
    }

    public static JavaPlugin getRegisterJavaPlugin(){

        return plugin;
    }
}
