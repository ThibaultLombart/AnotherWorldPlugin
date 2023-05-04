package fr.thybax.anotherworldplugin;

import fr.thybax.anotherworldplugin.economy.DatabaseController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenersLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        DatabaseController.savePlayer(player.getUniqueId());
    }
}
