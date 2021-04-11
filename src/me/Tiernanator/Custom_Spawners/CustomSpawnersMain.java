package me.Tiernanator.Custom_Spawners;

import org.bukkit.plugin.java.JavaPlugin;

import me.Tiernanator.Custom_Spawners.Commands.Skulls;
import me.Tiernanator.Custom_Spawners.Commands.Spawner;

public class CustomSpawnersMain extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    public void registerCommands() {
        getCommand("spawner").setExecutor(new Spawner(this));
        getCommand("skull").setExecutor(new Skulls(this));
    }

}
