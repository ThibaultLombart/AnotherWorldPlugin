package fr.thybax.anotherworldplugin.economy;

import fr.thybax.anotherworldplugin.ChatHexa;
import fr.thybax.anotherworldplugin.Informations;
import fr.thybax.anotherworldplugin.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CommandEconomy implements CommandExecutor {


    private static String prefix = Informations.getPrefix();
    private static String basicError = Informations.getBasicError();

    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();


            // ----------------------------------- MONEY ----------------------------------- \\
            if (cmd.getName().equalsIgnoreCase("money")) {
                if (args.length == 0) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.money") + Informations.getPlayerMoney().get(uuid) + Informations.getError("economy.symbol-money")));
                } else if (args.length != 1) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-money")));
                } else {
                    if (args[0].equalsIgnoreCase("money")) {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.money") + Informations.getPlayerMoney().get(uuid) + Informations.getError("economy.symbol-money")));
                    } else if (args[0].equalsIgnoreCase("ANOTHERCOINS")) {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.money") + Informations.getPlayerBMoney().get(uuid) + Informations.getError("economy.symbol-bmoney")));
                    } else if (args[0].equalsIgnoreCase("eventcoins")) {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.money") + Informations.getPlayerEMoney().get(uuid) + Informations.getError("economy.symbol-emoney")));
                    } else {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-money")));
                    }
                }


                // ----------------------------------- PAY ----------------------------------- \\
            } else if (cmd.getName().equalsIgnoreCase("pay")) {
                if (args.length != 3) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                } else {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player player2 = Bukkit.getPlayer(args[1]);
                        UUID player2UUID = player2.getUniqueId();
                        if (player2.equals(player)) {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.on-me")));
                        } else {
                            if (Informations.isDouble(args[2])) {
                                double somme = Double.parseDouble(args[2]);
                                // Si somme > 0
                                if (somme > 0) {


                                    fairePaiement(player,player2,args[0],somme);


                                } else {
                                    player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.under-0")));
                                }
                            } else {
                                player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.alphabet")));
                            }
                        }
                        } else{
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                        }
                    }


                // ----------------------------------- CHEQUE ----------------------------------- \\
            } else if (cmd.getName().equalsIgnoreCase("cheque")) {
                if (args.length != 2) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-cheque")));
                } else {
                    if (Informations.isDouble(args[1])) {
                        double somme = Double.parseDouble(args[1]);
                        // Si somme > 0
                        if (somme > 0) {
                            try {


                                faireCheque(player,Items.getCustomItem("cheque"),args[0],somme);


                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-under-0")));
                        }
                    }
                }


                // ----------------------------------- GIVE ECO ----------------------------------- \\
            } else if (cmd.getName().equalsIgnoreCase("giveeco")) {
                if (args.length != 3) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                } else {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player player2 = Bukkit.getPlayer(args[1]);
                        UUID player2UUID = player2.getUniqueId();
                        if (Informations.isDouble(args[2])) {
                            double somme = Double.parseDouble(args[2]);
                            // Si somme > 0
                            if (somme > 0) {


                                givePersonne(player,player2,args[0],somme);


                            } else {
                                player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.under-0")));
                            }
                        } else {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.alphabet")));
                        }
                    } else {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                    }
                }



            } else if (cmd.getName().equalsIgnoreCase("withdraweco")) {
                if (args.length != 3) {
                    player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                } else {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player player2 = Bukkit.getPlayer(args[1]);
                        UUID player2UUID = player2.getUniqueId();
                        if (Informations.isDouble(args[2])) {
                            double somme = Double.parseDouble(args[2]);
                            // Si somme > 0
                            if (somme > 0) {


                                withdrawPersonne(player,player2,args[0],somme);


                            } else {
                                player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.under-0")));
                            }
                        } else {
                            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.alphabet")));
                        }
                    } else {
                        player.sendMessage(ChatHexa.translateColorCodes(prefix + basicError + Informations.getError("errors.error-pay")));
                    }
                }
            }



        }
        return false;
    }

    public static boolean moneyVerification(Player player,String monnaie, double somme){
        UUID uuid = player.getUniqueId();

        if(monnaie.equalsIgnoreCase("MONEY")){
            if(somme <= Informations.getPlayerMoney().get(uuid)){
                player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-not-enough")));
                return false;
            }
        } else if (monnaie.equalsIgnoreCase("ANOTHERCOINS")){
            if(somme <= Informations.getPlayerBMoney().get(uuid)){
                player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-not-enough")));
                return false;
            }
        } else if (monnaie.equalsIgnoreCase("EVENTCOINS")) {
            if(somme <= Informations.getPlayerEMoney().get(uuid)){
                player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay-not-enough")));
                return false;
            }
        } else {
            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.not-known-money")));
            return false;
        }
        return true;
    }


    public static boolean fairePaiement (Player player,Player player2, String monnaie, double somme){
        if(!moneyVerification(player,monnaie,somme)){
            return false;
        }

        DatabaseController.retirerArgent(player.getUniqueId(), somme, monnaie);

        givePersonne(player,player2,monnaie,somme);


        return true;
    }

    public static boolean faireCheque (Player player,ItemStack cheque, String monnaie, double somme) throws Exception {

        if(!moneyVerification(player,monnaie,somme)){
            return false;
        }

        ItemMeta chequeMeta = cheque.getItemMeta();
        chequeMeta.setLore(Arrays.asList("","§eDescription : ","§f Cheque d'argent", "§f Monnaie : "+monnaie,"§f Somme : "+somme));
        List lore = chequeMeta.getLore();
        int id = DatabaseController.ajouterCheque(player.getUniqueId(),player.getName(),monnaie,somme);
        if (id >= 0) {
            lore.add("§f ID : " + id);
            DatabaseController.retirerArgent(player.getUniqueId(),somme,monnaie);
        } else {
            throw new Exception("Erreur dans la liaison a la base de donnée");
        }
        chequeMeta.setLore(lore);
        cheque.setItemMeta(chequeMeta);
        player.getInventory().addItem(cheque);



        return true;
    }

    public static boolean givePersonne (Player player, Player player2, String monnaie, double somme){
        String symbol = "";
        if(monnaie.equalsIgnoreCase("MONEY")){
            symbol = Informations.getError("economy.symbol-money");
        } else if (monnaie.equalsIgnoreCase("ANOTHERCOINS")){
            symbol = Informations.getError("economy.symbol-bmoney");
        } else if (monnaie.equalsIgnoreCase("EVENTCOINS")) {
            symbol = Informations.getError("economy.symbol-emoney");
        } else {
            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.not-known-money")));
            return false;
        }

        String[] message1 = {String.valueOf(somme), symbol, player2.getName()};
        MessageFormat messageFormat1 = new MessageFormat(Informations.getError("economy.pay.base"));
        player.sendMessage(ChatHexa.translateColorCodes(prefix + messageFormat1.format(message1)));

        DatabaseController.ajouterArgent(player2.getUniqueId(), somme, monnaie);

        String[] message2 = {String.valueOf(somme), symbol, player.getName()};
        MessageFormat messageFormat2 = new MessageFormat(Informations.getError("economy.pay.recieved"));
        player2.sendMessage(ChatHexa.translateColorCodes(prefix + messageFormat2.format(message2)));
        return true;
    }

    public static boolean withdrawPersonne (Player player, Player player2, String monnaie, double somme){
        String symbol = "";
        if(monnaie.equalsIgnoreCase("MONEY")){
            symbol = Informations.getError("economy.symbol-money");
        } else if (monnaie.equalsIgnoreCase("ANOTHERCOINS")){
            symbol = Informations.getError("economy.symbol-bmoney");
        } else if (monnaie.equalsIgnoreCase("EVENTCOINS")) {
            symbol = Informations.getError("economy.symbol-emoney");
        } else {
            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("economy.pay.not-known-money")));
            return false;
        }

        String[] message1 = {String.valueOf(somme), symbol, player2.getName()};
        MessageFormat messageFormat1 = new MessageFormat(Informations.getError("economy.withdraw.base"));
        player.sendMessage(ChatHexa.translateColorCodes(prefix + messageFormat1.format(message1)));

        DatabaseController.retirerArgent(player2.getUniqueId(), somme, monnaie);

        String[] message2 = {player.getName(),String.valueOf(somme), symbol};
        MessageFormat messageFormat2 = new MessageFormat(Informations.getError("economy.withdraw.recieved"));
        player2.sendMessage(ChatHexa.translateColorCodes(prefix + messageFormat2.format(message2)));
        return true;
    }


}
