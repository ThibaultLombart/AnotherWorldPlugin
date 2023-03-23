package fr.thybax.anotherworldplugin.trade;

import fr.thybax.anotherworldplugin.ChatHexa;
import fr.thybax.anotherworldplugin.Informations;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class CommandTrade implements CommandExecutor {

    private static HashMap<UUID, String> hash = new HashMap<UUID, String>();

    private static String prefix = Informations.getPrefix();
    private static String basicError = Informations.getBasicError();

    public static HashMap<UUID, String> getHash(){
        return hash;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg0, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 1){
                if(Bukkit.getPlayer(args[0]) != null){
                    Player player2 = Bukkit.getPlayer(args[0]);
                    UUID player2UUID = player2.getUniqueId();
                    if(player2.equals(player)){
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("messages.trade-on-me")));
                    } else {
                        if(hash.containsKey(player2UUID)){
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("errors.error-trade-already")));
                        } else {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("messages.trade-send")));
                            player2.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("messages.trade-received") + player.getName() + " " + Informations.getError("messages.trade-foraccept")));
                            hash.put(player2UUID,player.getName());
                        }
                    }
                } else {
                    if(args[0].equalsIgnoreCase("accept")){
                        if(hash.containsKey(player.getUniqueId())){
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("messages.trade-accept")));
                            String player2name = hash.get(player.getUniqueId());
                            if(Bukkit.getPlayer(player2name) != null){
                                Player player2 = Bukkit.getPlayer(player2name);
                                player2.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("messages.trade-accept2")));

                                Inventory inv = Bukkit.createInventory(null, 45, "§2Menu de Trade");

                                ItemStack grey = new ItemStack(Material.GRAY_STAINED_GLASS_PANE,1);
                                ItemMeta customGrey = grey.getItemMeta();
                                customGrey.setDisplayName(" ");
                                grey.setItemMeta(customGrey);

                                ItemStack green = new ItemStack(Material.LIME_WOOL,1);
                                ItemMeta customGreen = green.getItemMeta();
                                customGreen.setDisplayName("§aAnnuler");
                                green.setItemMeta(customGreen);


                                ItemStack red = new ItemStack(Material.RED_WOOL,1);
                                ItemMeta customRed = red.getItemMeta();
                                customRed.setDisplayName("§4Confirmer");
                                red.setItemMeta(customRed);


                                                      inv.setItem(4,grey);
                                                      inv.setItem(13,grey);
                                                      inv.setItem(22,grey);
                                                      inv.setItem(31,grey);
                                inv.setItem(36,grey);inv.setItem(37,grey);inv.setItem(38,grey);inv.setItem(39,red);inv.setItem(40,grey);inv.setItem(41,red);inv.setItem(42,grey);inv.setItem(43,grey);inv.setItem(44,grey);



                                player.openInventory(inv);
                                player2.openInventory(inv);


                            } else {
                                player.sendMessage(ChatHexa.translateColorCodes(prefix+ Informations.getError("messages.trade-cancelled-connection")));
                            }
                            hash.remove(player.getUniqueId());
                        } else {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("errors.error-trade-nothing")));
                        }
                    } else if(args[0].equalsIgnoreCase("deny")){
                        if(hash.containsKey(player.getUniqueId())){
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("messages.trade-refused")));
                            String player2Uuid = hash.get(player.getUniqueId());
                            if(Bukkit.getPlayer(player2Uuid) != null){
                                Bukkit.getPlayer(player2Uuid).sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("messages.trade-refused2")));
                            }
                            hash.remove(player.getUniqueId());
                        } else {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("errors.error-trade-nothing")));
                        }
                    } else {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-trade")));
                    }
                }
            } else {
                player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-trade")));
            }
        }
        return false;
    }
}
