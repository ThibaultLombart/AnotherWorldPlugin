package fr.thybax.anotherworldplugin.trade;

import fr.thybax.anotherworldplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ListenersTrade implements Listener {

    HashMap<UUID, Boolean> hashTrade = new HashMap<>();
    ArrayList<Player> arrayPlayer = new ArrayList<>();

    ArrayList<Player> itsOk = new ArrayList<>();


    @EventHandler
    public void onClick(InventoryClickEvent event){
        ItemStack grey = new ItemStack(Material.GRAY_STAINED_GLASS_PANE,1);
        ItemMeta customGrey = grey.getItemMeta();
        customGrey.setDisplayName(" ");
        grey.setItemMeta(customGrey);

        ItemStack yellow = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE,1);
        ItemMeta customYellow = yellow.getItemMeta();
        customYellow.setDisplayName(" ");
        yellow.setItemMeta(customYellow);

        ItemStack green = new ItemStack(Material.LIME_WOOL,1);
        ItemMeta customGreen = green.getItemMeta();
        customGreen.setDisplayName("§aAnnuler");
        green.setItemMeta(customGreen);


        ItemStack red = new ItemStack(Material.RED_WOOL,1);
        ItemMeta customRed = red.getItemMeta();
        customRed.setDisplayName("§4Confirmer");
        red.setItemMeta(customRed);

        Inventory inv = event.getInventory();
        Inventory invClicked = event.getClickedInventory();
        Player playerTest1 = (Player) event.getWhoClicked();
        HashMap<UUID, String> hash = CommandTrade.getHash();

        List<HumanEntity> test = event.getViewers();
        if(test.size() == 1){
            return;
        } else {

            Player player1 = (Player) test.get(1);
            Player player2 = (Player) test.get(0);

            int[] compteARebours = {40,31,22,13,4};

            int[] player1L = {30,29,28,27,21,20,19,18,12,11,10,9,3,2,1,0};
            int[] player2L = {35,34,33,32,26,25,24,23,17,16,15,14,8,7,6,5};
            ArrayList<Integer> player1List = new ArrayList<Integer>();
            ArrayList<Integer> player2List = new ArrayList<Integer>();

            for(int i = 0;i < player1L.length; i++){
                player1List.add(player1L[i]);
                player2List.add(player2L[i]);
            }

            player1List.add(39);
            player2List.add(41);



            if(event.getView().getTitle().equalsIgnoreCase("§2Menu de Trade")){
                if(event.getClick() == ClickType.DOUBLE_CLICK || event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT || event.getClick() == ClickType.CONTROL_DROP || event.getClick() == ClickType.UNKNOWN){
                    event.setResult(Event.Result.DENY);
                }
                if(inv == invClicked){
                    if(event.getCurrentItem() != null){
                        ItemStack current = event.getCurrentItem();
                        if(current.equals(grey)){
                            event.setCancelled(true);
                        } else {
                            if(playerTest1.equals(player1)){
                                if(player1List.contains(event.getSlot())){
                                    if(current.equals(red)){
                                        event.setCurrentItem(green);
                                        event.setCancelled(true);
                                        if(inv.getItem(41).equals(green)){
                                            itsOk.add(player1);
                                            event.setCancelled(true);
                                            hashTrade.put(player1.getUniqueId(),true);
                                            final int[] countdown = {5};

                                            BukkitScheduler scheduler = Bukkit.getServer().getScheduler(); scheduler.scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (countdown[0] > 0) {
                                                        inv.setItem(compteARebours[countdown[0]-1],yellow);
                                                        countdown[0]--;
                                                    } else {
                                                        // le compte à rebours est terminé
                                                        scheduler.cancelTasks(Main.getInstance());
                                                        if(itsOk.contains(player1) || itsOk.contains(player2)){
                                                            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                                                                for (int slot : player1L) {
                                                                    ItemStack item = inv.getItem(slot);
                                                                    if (item != null && !item.getType().isAir()) {
                                                                        player2.getInventory().addItem(item);
                                                                        inv.clear(slot);
                                                                    }
                                                                }
                                                                for (int slot : player2L) {
                                                                    ItemStack item = inv.getItem(slot);
                                                                    if (item != null && !item.getType().isAir()) {
                                                                        player1.getInventory().addItem(item);
                                                                        inv.clear(slot);
                                                                    }
                                                                }
                                                            });

                                                            arrayPlayer.add(player1);
                                                            arrayPlayer.add(player2);

                                                            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                                                                player1.closeInventory();
                                                                player2.closeInventory();
                                                                arrayPlayer.remove(player1);
                                                                arrayPlayer.remove(player2);
                                                                hashTrade.remove(player1.getUniqueId());hashTrade.remove(player2.getUniqueId());
                                                            }, 1L);
                                                        } else {
                                                            for(int i = 0;i < compteARebours.length; i++){
                                                                inv.setItem(compteARebours[i],grey);
                                                            }
                                                        }
                                                    }
                                                }
                                            }, 0L, 20L);
                                        } else {
                                            hashTrade.put(player1.getUniqueId(),true);
                                        }
                                    } else if (current.equals(green)){
                                        if(inv.getItem(41).equals(green)){
                                            itsOk.remove(player1);
                                        }
                                        event.setCurrentItem(red);
                                        event.setCancelled(true);
                                        hashTrade.remove(player1.getUniqueId());
                                    } else {
                                        if(hashTrade.containsKey(player1.getUniqueId())){
                                            event.setCancelled(true);
                                        }
                                    }
                                } else {
                                    event.setCancelled(true);
                                }
                            } else {
                                if(player2List.contains(event.getSlot())){
                                    if(current.equals(red)){
                                        event.setCurrentItem(green);
                                        if(inv.getItem(39).equals(green)){
                                            hashTrade.put(player2.getUniqueId(),true);
                                            event.setCancelled(true);
                                            itsOk.add(player1);
                                            final int[] countdown = {5};

                                            BukkitScheduler scheduler = Bukkit.getServer().getScheduler(); scheduler.scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (countdown[0] > 0) {
                                                        inv.setItem(compteARebours[countdown[0]-1],yellow);
                                                        countdown[0]--;
                                                    } else {
                                                        // le compte à rebours est terminé
                                                        scheduler.cancelTasks(Main.getInstance());
                                                        if(itsOk.contains(player1) || itsOk.contains(player2)){
                                                            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                                                                for (int slot : player1L) {
                                                                    ItemStack item = inv.getItem(slot);
                                                                    if (item != null && !item.getType().isAir()) {
                                                                        player2.getInventory().addItem(item);
                                                                        inv.clear(slot);
                                                                    }
                                                                }
                                                                for (int slot : player2L) {
                                                                    ItemStack item = inv.getItem(slot);
                                                                    if (item != null && !item.getType().isAir()) {
                                                                        player1.getInventory().addItem(item);
                                                                        inv.clear(slot);
                                                                    }
                                                                }
                                                            });

                                                            arrayPlayer.add(player1);
                                                            arrayPlayer.add(player2);

                                                            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                                                                player1.closeInventory();
                                                                player2.closeInventory();
                                                                arrayPlayer.remove(player1);
                                                                arrayPlayer.remove(player2);
                                                                hashTrade.remove(player1.getUniqueId());hashTrade.remove(player2.getUniqueId());
                                                            }, 1L);
                                                        } else {
                                                            for(int i = 0;i < compteARebours.length; i++){
                                                                inv.setItem(compteARebours[i],grey);
                                                            }
                                                        }
                                                    }
                                                }
                                            }, 0L, 20L);
                                        } else {
                                            event.setCancelled(true);
                                            hashTrade.put(player2.getUniqueId(),true);
                                        }
                                    } else if (current.equals(green)){
                                        if(inv.getItem(39).equals(green)){
                                            itsOk.remove(player1);
                                        }
                                        event.setCurrentItem(red);
                                        event.setCancelled(true);
                                        hashTrade.remove(player2.getUniqueId());
                                    } else {
                                        if(hashTrade.containsKey(player2.getUniqueId())){
                                            event.setCancelled(true);
                                        }
                                    }
                                } else {
                                    event.setCancelled(true);
                                }
                            }
                        }
                    } else {
                        if(playerTest1.equals(player1)){
                            if(!player1List.contains(event.getSlot())){
                                event.setCancelled(true);
                            }
                        } else {
                            if(!player2List.contains(event.getSlot())){
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }

        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(event.getView().getTitle().equalsIgnoreCase("§2Menu de Trade")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(InventoryCloseEvent event){
        Inventory inv = event.getInventory();
        Player quitPlayer = (Player) event.getPlayer();

        if(arrayPlayer.contains(quitPlayer)){
            return;
        } else {


            if (event.getView().getTitle().equalsIgnoreCase("§2Menu de Trade")) {
                Player player1 = (Player) event.getViewers().get(1);
                Player player2 = (Player) event.getViewers().get(0);

                int[] player1L = {30,29,28,27,21,20,19,18,12,11,10,9,3,2,1,0};
                int[] player2L = {35,34,33,32,26,25,24,23,17,16,15,14,8,7,6,5};

                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    for (int slot : player1L) {
                        ItemStack item = inv.getItem(slot);
                        if (item != null && !item.getType().isAir()) {
                            player1.getInventory().addItem(item);
                            inv.clear(slot);
                        }
                    }
                    for (int slot : player2L) {
                        ItemStack item = inv.getItem(slot);
                        if (item != null && !item.getType().isAir()) {
                            player2.getInventory().addItem(item);
                            inv.clear(slot);
                        }
                    }
                });

                arrayPlayer.add(player1);
                arrayPlayer.add(player2);

                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    if(player1.equals(quitPlayer)){
                        player2.closeInventory();
                    } else {
                        player1.closeInventory();
                    }
                    arrayPlayer.remove(player1);
                    arrayPlayer.remove(player2);
                }, 1L);
            }
        }
    }
}
