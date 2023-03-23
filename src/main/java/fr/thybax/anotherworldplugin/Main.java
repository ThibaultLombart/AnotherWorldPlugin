package fr.thybax.anotherworldplugin;

import fr.thybax.anotherworldplugin.database.DatabaseManager;
import fr.thybax.anotherworldplugin.economy.CommandEconomy;
import fr.thybax.anotherworldplugin.economy.DatabaseController;
import fr.thybax.anotherworldplugin.items.CommandItems;
import fr.thybax.anotherworldplugin.items.Items;
import fr.thybax.anotherworldplugin.items.ListenersItems;
import fr.thybax.anotherworldplugin.trade.CommandTrade;
import fr.thybax.anotherworldplugin.trade.ListenersTrade;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {


    private  static Main instance;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Informations.init(getConfig());
        DatabaseController.init(this);
        System.out.println("Plugin Activé");
        Items.init();
        getCommand("giveitem").setExecutor(new CommandItems());
        getCommand("trade").setExecutor(new CommandTrade());
        getCommand("money").setExecutor(new CommandEconomy());
        getCommand("pay").setExecutor(new CommandEconomy());
        getServer().getPluginManager().registerEvents(new ListenersItems(), this);
        getServer().getPluginManager().registerEvents(new ListenersTrade(), this);
        getServer().getPluginManager().registerEvents(new ListenersJoin(this), this);
        // Plugin startup logic
        databaseManager = new DatabaseManager();
    }

    public static Main getInstance(){
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void onDisable() {
        this.databaseManager.close();
        // Plugin shutdown logic
    }

}
