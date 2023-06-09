package fr.thybax.anotherworldplugin.economy;

import fr.thybax.anotherworldplugin.Exceptions.SqlErrorException;
import fr.thybax.anotherworldplugin.Informations;
import fr.thybax.anotherworldplugin.Main;
import fr.thybax.anotherworldplugin.database.DbConnection;
import fr.thybax.anotherworldplugin.items.Items;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ListenersEco implements Listener {

    private Main main;
    public ListenersEco(Main main){
        this.main = main;
    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && Boolean.TRUE.equals(Items.equalsLocalizedName(item,Items.getCustomItem("cheque")))){
                event.setCancelled(true);
                List<String> itemLore = item.getItemMeta().getLore();
                int longueur = itemLore.size();
                String idResult = itemLore.get(longueur-1);
                String[] subs3 = idResult.split(" ");
                int id = Integer.parseInt(subs3[3]);



                final DbConnection databaseManager = main.getDatabaseManager().getDbConnection();

                    try (final Connection connection = databaseManager.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement("SELECT used,nickname FROM cheque WHERE ID = ?");final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE CHEQUE SET used = 1 WHERE ID = ?")){


                        preparedStatement.setInt(1, id);
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        if(resultSet.next()) {
                            if (resultSet.getInt(1) == 0) {
                                preparedStatement2.setInt(1, id);
                                preparedStatement2.executeUpdate();

                                String result = itemLore.get(longueur - 2);
                                String result2 = itemLore.get(longueur - 3);
                                String[] subs = result.split(" ");
                                String[] subs2 = result2.split(" ");
                                double finishResult = Double.parseDouble(subs[3]);
                                String finalResultString = subs2[3];

                                player.getInventory().remove(item);
                                Informations.addMoney(player.getUniqueId(), finishResult, finalResultString);

                            } else {
                                player.getInventory().removeItem(item);
                                String pseudo = resultSet.getString(2);

                                System.out.println(pseudo);

                                player.getServer().getBanList(BanList.Type.NAME).addBan(player.getName(), "Duplication \n Si cela vous parait injustifié, veuillez nous contacter sur Discord", null, "Serveur");
                                player.kickPlayer("Vous avez été ban pour : Duplication \n Si cela vous parait injustifié, veuillez nous contacter sur Discord");
                                Logger.getLogger("Le joueur : " + player.getName() + " a été banni pour Duplication (Au niveau des Cheques)");
                                player.getServer().getBanList(BanList.Type.NAME).addBan(pseudo, "Duplication \n Si cela vous parait injustifié, veuillez nous contacter sur Discord", null, "Serveur");

                                if (Bukkit.getPlayer(pseudo) != null){
                                    Bukkit.getPlayer(pseudo).kickPlayer("Vous avez été ban pour : Duplication \n Si cela vous parait injustifié, veuillez nous contacter sur Discord");
                                }
                                Logger.getLogger("Le joueur : " + pseudo + " a été banni pour Duplication (Au niveau des Cheques)");
                            }
                        }

                    } catch (Exception e) {
                        throw new SqlErrorException(e + "---------- Erreur lors du Cheque");
                    }

        }
    }
}
