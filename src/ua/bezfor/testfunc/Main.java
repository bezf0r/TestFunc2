package ua.bezfor.testfunc;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("sc").setExecutor(new timerStarter());
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
