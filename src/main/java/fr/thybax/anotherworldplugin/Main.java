package fr.thybax.anotherworldplugin;

import fr.thybax.anotherworldplugin.database.DatabaseManager;
import fr.thybax.anotherworldplugin.economy.*;
import fr.thybax.anotherworldplugin.items.CommandItems;
import fr.thybax.anotherworldplugin.items.Items;
import fr.thybax.anotherworldplugin.items.ListenersItems;
import fr.thybax.anotherworldplugin.shop.ListenersShop;
import fr.thybax.anotherworldplugin.shop.Shop;
import fr.thybax.anotherworldplugin.shop.ShopManager;
import fr.thybax.anotherworldplugin.tabcompletion.TabCompletionGiveItem;
import fr.thybax.anotherworldplugin.tabcompletion.TabCompletionMoney;
import fr.thybax.anotherworldplugin.tabcompletion.TabCompletionPay;
import fr.thybax.anotherworldplugin.trade.CommandTrade;
import fr.thybax.anotherworldplugin.trade.ListenersTrade;
import fr.thybax.anotherworldplugin.utils.CommandAnotherWorld;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private DatabaseManager databaseManager;
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Informations.init(getConfig());
        DatabaseController.init(this);
        Items.init();
        ShopManager.init();
        getCommand("giveitem").setExecutor(new CommandItems());
        getCommand("giveitem").setTabCompleter(new TabCompletionGiveItem());

        getCommand("trade").setExecutor(new CommandTrade());


        getCommand("money").setExecutor(new CommandEconomy());
        getCommand("money").setTabCompleter(new TabCompletionMoney());

        getCommand("pay").setExecutor(new CommandEconomy());
        getCommand("pay").setTabCompleter(new TabCompletionPay());

        getCommand("cheque").setExecutor(new CommandEconomy());
        getCommand("cheque").setTabCompleter(new TabCompletionMoney());

        getCommand("giveeco").setExecutor(new CommandEconomy());
        getCommand("giveeco").setTabCompleter(new TabCompletionPay());

        getCommand("withdraweco").setExecutor(new CommandEconomy());
        getCommand("withdraweco").setTabCompleter(new TabCompletionPay());

        getCommand("anotherReload").setExecutor(new CommandAnotherWorld());

        getCommand("shop").setExecutor(new Shop());

        getServer().getPluginManager().registerEvents(new ListenersItems(), this);
        getServer().getPluginManager().registerEvents(new ListenersTrade(), this);
        getServer().getPluginManager().registerEvents(new ListenersShop(), this);
        getServer().getPluginManager().registerEvents(new ListenersJoin(this), this);
        getServer().getPluginManager().registerEvents(new ListenersEco(this), this);
        // Plugin startup logic
        databaseManager = new DatabaseManager();
        Informations.initMain(this);
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static Main getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        this.databaseManager.close();
        // Plugin shutdown logic
    }

}
