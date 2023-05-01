package fr.thybax.anotherworldplugin.items;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandItems implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(cmd.getName().equalsIgnoreCase("giveitem")) {
                if(args.length != 1) {
                    player.sendMessage("Soit t'as rien mis, soit t'as trop mis");
                } else{
                    if(args[0].equalsIgnoreCase("infinitelavabucket")){
                        ItemStack infiniteLavaBucket = Items.getCustomItem("infinitelavabucket");
                        player.getInventory().addItem(infiniteLavaBucket);
                        player.updateInventory();
                    }
                    if(args[0].equalsIgnoreCase("infinitewaterbucket")){
                        ItemStack infiniteWaterBucket = Items.getCustomItem("infinitewaterbucket");
                        player.getInventory().addItem(infiniteWaterBucket);
                        player.updateInventory();
                    }
                    if(args[0].equalsIgnoreCase("infiniteemptybucket")){
                        ItemStack infiniteEmptyBucket = Items.getCustomItem("infiniteemptybucket");
                        player.getInventory().addItem(infiniteEmptyBucket);
                        player.updateInventory();
                    }
                }
            }

        }
        return false;
    }
}