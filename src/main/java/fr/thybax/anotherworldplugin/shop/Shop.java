package fr.thybax.anotherworldplugin.shop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Shop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player player) {

            // Si il effectue simplement un /shop
            if(args.length == 0){
                Inventory inv = ShopManager.getShopMenu();
                player.openInventory(inv);

            }
        }

        return false;
    }
}
