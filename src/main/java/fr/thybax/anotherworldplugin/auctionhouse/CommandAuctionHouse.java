package fr.thybax.anotherworldplugin.auctionhouse;

import fr.thybax.anotherworldplugin.ChatHexa;
import fr.thybax.anotherworldplugin.Informations;
import fr.thybax.anotherworldplugin.shop.ShopManager;
import net.minecraft.world.item.Item;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class CommandAuctionHouse implements CommandExecutor {
    private String prefix = Informations.getPrefix();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player player) {

            // Si il effectue simplement un /ah
            if(args.length == 0){
                AuctionHouseManager.createAuctionMenuPage(player,0);
                player.sendMessage(AuctionHouseManager.itemsManager.toString());

            } else {
                // Si il y a + qu'un /ah (donc ici /ah sell <prix>)
                if(args.length == 2){
                    if(args[0].equals("sell")) {
                        int prix;
                        try {
                            prix = Integer.parseInt(args[1]);
                        } catch (Exception e) {
                            player.sendMessage("Veuillez mettre un nombre entier pour votre prix");
                            return false;
                        }
                        if(prix > 0){
                            ItemStack item = player.getInventory().getItemInMainHand();
                            AuctionHouseManager.itemsManager.addItem(item,prix,player.getUniqueId());
                            player.getInventory().setItemInMainHand(null);
                        } else {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.under-0")));
                        }
                    }
                }
            }
        }

        return false;
    }
}
