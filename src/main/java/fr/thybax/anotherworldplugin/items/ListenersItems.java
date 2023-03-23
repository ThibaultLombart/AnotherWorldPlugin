package fr.thybax.anotherworldplugin.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ListenersItems implements Listener {

    @EventHandler
    public void onBucketPlace(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        EquipmentSlot hand = event.getHand();

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if(Items.equalsLocalizedName(item,Items.getCustomItem("infinitelavabucket"))){
                event.setCancelled(true);
                event.getClickedBlock().getRelative(event.getBlockFace()).setType(Material.LAVA);
            }
            if(Items.equalsLocalizedName(item,Items.getCustomItem("infinitewaterbucket"))){
                event.setCancelled(true);
                event.getClickedBlock().getRelative(event.getBlockFace()).setType(Material.WATER);
            }
        }
    }


}