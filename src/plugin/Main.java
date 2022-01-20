package plugin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.commands.CommandKit;
import plugin.events.EventTemplate;

public class Main extends JavaPlugin{


    @Override
    public void onEnable() {
        //this.getCommand("kit").setExecutor(new CommandKit());
        this.getCommand("gamer").setExecutor(new CommandKit());
        getServer().getPluginManager().registerEvents(new EventTemplate(), this);
        getLogger().info(ChatColor.GOLD + "MinecraftRP is now enabled!");
    }


    @Override
    public void onDisable() {

    }
};



