package me.combathax.worlds;

import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Worlds extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        for (File f : getServer().getWorldContainer().listFiles()) {
            if (f.isDirectory()) {
                for (String s : f.list()) {
                    if (s.equals("level.dat")) {
                        getServer().createWorld(new WorldCreator(f.getName()));
                    }
                }
            }
        }
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Worlds plugin is Enabled");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
