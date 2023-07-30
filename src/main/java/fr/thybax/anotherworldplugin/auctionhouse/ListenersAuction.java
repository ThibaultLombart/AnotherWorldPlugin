package fr.thybax.anotherworldplugin.auctionhouse;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenersAuction implements Listener {

    private String nameMenu = "§aAuctionHouse";

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(nameMenu)){

            ItemStack prochain = new ItemStack(Material.PAPER,1);
            ItemMeta customProchain = prochain.getItemMeta();
            customProchain.setDisplayName("§fProchaine Page");
            prochain.setItemMeta(customProchain);


            if(event.getCurrentItem() != null){
                ItemStack itemClicked = event.getCurrentItem();
            }
        }
    }
}
