package fr.thybax.anotherworldplugin.auctionhouse;

import fr.thybax.anotherworldplugin.Informations;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionHouseManager {

    private AuctionHouseManager() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemsManager itemsManager;

    public static void initAuction(){
        itemsManager = new ItemsManager();
    }


    public static void createAuctionMenuPage(Player player, int page){
        ArrayList<ItemsManager.ItemsAuction> listeItems = itemsManager.getListeItem();
        Inventory inv = Bukkit.createInventory(null, 54, "§aAuctionHouse");

        // Ici on met donc le tournesol avec la page
        ItemStack tournesol = new ItemStack(Material.SUNFLOWER,1);
        ItemMeta customTournesol = tournesol.getItemMeta();
        customTournesol.setDisplayName("§fPage "+page);
        tournesol.setItemMeta(customTournesol);

        // On l'ajoute a l'inventaire
        inv.setItem(49,tournesol);

        if(!listeItems.isEmpty()){
            // Si la liste des items n'est pas vide
            if(listeItems.size() > 46 + 45*page){
                ItemStack prochain = new ItemStack(Material.PAPER,1);
                ItemMeta customProchain = prochain.getItemMeta();
                customProchain.setDisplayName("§fProchaine Page");
                prochain.setItemMeta(customProchain);

                // On l'ajoute a l'inventaire
                inv.setItem(50,prochain);
            }

            if(page >= 1){
                ItemStack avant = new ItemStack(Material.PAPER,1);
                ItemMeta customAvant = avant.getItemMeta();
                customAvant.setDisplayName("§fPrecedente Page");
                avant.setItemMeta(customAvant);

                // On l'ajoute a l'inventaire
                inv.setItem(48,avant);
            }
            int compteur = 0;
            if(page == 0){
                for(int i = 0; i < listeItems.size() && i < 45; i++){
                    // On récupere toutes les infos
                    ItemsManager.ItemsAuction itemsAuction = listeItems.get(i);
                    UUID uuid = itemsAuction.getOwner();
                    String name = Bukkit.getPlayer(uuid).getDisplayName();
                    ItemStack itemAMettre = itemsAuction.getItem().clone();
                    String temps = itemsAuction.tempsRestantFormate();
                    int prix = itemsAuction.getPrice();

                    // On rentre les infos
                    ItemMeta itemMeta = itemAMettre.getItemMeta();
                    List<String> lore;
                    if(itemMeta.getLore() == null){
                        lore = new ArrayList<>();
                    } else {
                        lore = itemMeta.getLore();
                    }
                    lore.add("§f--------------------");
                    lore.add("§a Cliquez ici pour Acheter");
                    lore.add(" ");
                    lore.add("§9 Prix : §f"+prix+ Informations.getError("economy.symbol-money"));
                    lore.add("§9 Vendeur : §f"+name);
                    lore.add("§9 Temps Restant : §f"+temps);
                    lore.add("§f--------------------");
                    itemMeta.setLore(lore);
                    itemAMettre.setItemMeta(itemMeta);

                    inv.setItem(compteur,itemAMettre);
                    compteur ++;

                }
            } else {
                for(int i = (45*page)+1; i < listeItems.size() && i < (45*page)+1+45; i++){

                }
            }
        }





        player.openInventory(inv);
    }
}
