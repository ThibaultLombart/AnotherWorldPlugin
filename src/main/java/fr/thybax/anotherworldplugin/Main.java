package fr.thybax.anotherworldplugin;

import fr.thybax.anotherworldplugin.auctionhouse.AuctionHouseManager;
import fr.thybax.anotherworldplugin.auctionhouse.CommandAuctionHouse;
import fr.thybax.anotherworldplugin.auctionhouse.ListenersAuction;
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
import org.bukkit.scheduler.BukkitRunnable;

public final class Main extends JavaPlugin {

    private DatabaseManager databaseManager;
    private static Main instance;

    private int intervalEnTicks; // L'intervalle entre chaque sauvegarde en ticks
    private int taskId; // L'ID de la tâche planifiée


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

        getCommand("auctionhouse").setExecutor(new CommandAuctionHouse());

        getServer().getPluginManager().registerEvents(new ListenersItems(), this);
        getServer().getPluginManager().registerEvents(new ListenersTrade(), this);
        getServer().getPluginManager().registerEvents(new ListenersShop(), this);
        getServer().getPluginManager().registerEvents(new ListenersJoin(this), this);
        getServer().getPluginManager().registerEvents(new ListenersLeave(), this);
        getServer().getPluginManager().registerEvents(new ListenersEco(this), this);

        getServer().getPluginManager().registerEvents(new ListenersAuction(), this);
        // Plugin startup logic
        databaseManager = new DatabaseManager();
        Informations.initMain(this);
        AuctionHouseManager.initAuction();

        // Initialisation de l'intervalle (exemple : sauvegarde toutes les 10 minutes)
        intervalEnTicks = 20 * 60 * 10;

        // Planifier la sauvegarde à intervalles réguliers
        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                // Appeler la fonction de sauvegarde ici
                Informations.saveAll();
            }
        }.runTaskTimer(this, intervalEnTicks, intervalEnTicks).getTaskId();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static Main getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        Informations.saveAll();
        getServer().getScheduler().cancelTask(taskId);

        this.databaseManager.close();
    }

}
