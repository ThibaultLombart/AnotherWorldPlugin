package fr.thybax.anotherworldplugin.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

public class ListenersItems implements Listener {

    @EventHandler
    public void onBucketPlace(PlayerBucketEmptyEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block b = event.getBlock();

        int x = event.getBlock().getX();
        int y = event.getBlock().getY();
        int z = event.getBlock().getZ();



        if(!item.hasItemMeta()) return;

        if(Boolean.TRUE.equals(Items.equalsLocalizedName(item,Items.getCustomItem("infinitewaterbucket")))){
            event.setCancelled(true);
            if(b.getBlockData() instanceof Waterlogged wl) {
                wl.setWaterlogged(true);
                b.setBlockData(wl);
            } else {
                player.getWorld().getBlockAt(x, y, z).setType(Material.WATER);
            }
        } else if (Boolean.TRUE.equals(Items.equalsLocalizedName(item,Items.getCustomItem("infinitelavabucket")))){
            event.setCancelled(true);
            player.getWorld().getBlockAt(x, y, z).setType(Material.LAVA);
        }

    }

    @EventHandler
    public void onBucketRefill(PlayerBucketFillEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block b = event.getBlock();

        int x = event.getBlock().getX();
        int y = event.getBlock().getY();
        int z = event.getBlock().getZ();



        if(!item.hasItemMeta()) return;

        if(Boolean.TRUE.equals(Items.equalsLocalizedName(item,Items.getCustomItem("infiniteemptybucket")))){
            event.setCancelled(true);
            if(b.getBlockData() instanceof Waterlogged wl) {
                wl.setWaterlogged(false);
                b.setBlockData(wl);
            } else {
                player.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
            }
        }
    }


}