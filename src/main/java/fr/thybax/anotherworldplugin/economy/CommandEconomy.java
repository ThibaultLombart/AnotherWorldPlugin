package fr.thybax.anotherworldplugin.economy;

import fr.thybax.anotherworldplugin.ChatHexa;
import fr.thybax.anotherworldplugin.Informations;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandEconomy implements CommandExecutor {


    private static String prefix = Informations.getPrefix();
    private static String basicError = Informations.getBasicError();

    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (cmd.getName().equalsIgnoreCase("money")) {
                if(args.length != 1) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-money")));
                } else {
                    if(args[0].equalsIgnoreCase("money")){
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.money") + Informations.getPlayerMoney().get(uuid) + Informations.getError("economy.symbol-money")));
                    } else if (args[0].equalsIgnoreCase("boutiquecoins")) {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.money") + Informations.getPlayerBMoney().get(uuid) + Informations.getError("economy.symbol-bmoney")));
                    } else if (args[0].equalsIgnoreCase("eventcoins")) {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.money") + Informations.getPlayerEMoney().get(uuid) + Informations.getError("economy.symbol-emoney")));
                    } else {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-money")));
                    }
                }
            } else if (cmd.getName().equalsIgnoreCase("pay")) {
                if(args.length != 3) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                } else {
                    if(Bukkit.getPlayer(args[1]) != null){
                        Player player2 = Bukkit.getPlayer(args[1]);
                        UUID player2UUID = player2.getUniqueId();
                        if(player2.equals(player)){
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-on-me")));
                        } else {
                            if(Informations.isDouble(args[2])){
                                double somme = Double.parseDouble(args[2]);
                                // Si somme > 0
                                if(somme > 0){
                                    if(args[0].equalsIgnoreCase("money")){
                                        if(somme <= Informations.getPlayerMoney().get(uuid)){
                                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay") + somme  + Informations.getError("economy.symbol-money")) + Informations.getError("economy.pay-continue") + player2.getName());
                                            DatabaseController.retirerArgent(uuid,somme,"MONEY");
                                            DatabaseController.ajouterArgent(player2UUID,somme,"MONEY");
                                            player2.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-recieved") + somme  + Informations.getError("economy.symbol-money")) + Informations.getError("economy.pay-recieved-continue") + player.getName());
                                        } else {
                                            player.sendMessage(ChatHexa.translateColorCodes(prefix+Informations.getError("economy.pay-not-enough")));
                                        }
                                    } else if (args[0].equalsIgnoreCase("boutiquecoins")) {
                                        if(somme <= Informations.getPlayerBMoney().get(uuid)){
                                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay") + somme  + Informations.getError("economy.symbol-bmoney")) + Informations.getError("economy.pay-continue") + player2.getName());
                                            DatabaseController.retirerArgent(uuid,somme,"BOUTIQUECOINS");
                                            DatabaseController.ajouterArgent(player2UUID,somme,"BOUTIQUECOINS");
                                            player2.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-recieved") + somme  + Informations.getError("economy.symbol-bmoney")) + Informations.getError("economy.pay-recieved-continue") + player.getName());
                                        }  else {
                                            player.sendMessage(ChatHexa.translateColorCodes(prefix+Informations.getError("economy.pay-not-enough")));
                                        }
                                    } else if (args[0].equalsIgnoreCase("eventcoins")) {
                                        if(somme <= Informations.getPlayerEMoney().get(uuid)){
                                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay") + somme  + Informations.getError("economy.symbol-emoney")) + Informations.getError("economy.pay-continue") + player2.getName());
                                            DatabaseController.retirerArgent(uuid,somme,"EVENTCOINS");
                                            DatabaseController.ajouterArgent(player2UUID,somme,"EVENTCOINS");
                                            player2.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-recieved") + somme  + Informations.getError("economy.symbol-emoney")) + Informations.getError("economy.pay-recieved-continue") + player.getName());

                                        } else {
                                            player.sendMessage(ChatHexa.translateColorCodes(prefix+Informations.getError("economy.pay-not-enough")));
                                        }
                                    } else {
                                        player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                                    }
                                } else {
                                    player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-under-0")));
                                }
                            } else {
                                player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-alphabet")));
                            }
                        }
                    } else {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                    }
                }

            }
        }
        return false;
    }
}
