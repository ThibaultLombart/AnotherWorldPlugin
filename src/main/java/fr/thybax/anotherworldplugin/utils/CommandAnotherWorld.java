package fr.thybax.anotherworldplugin.utils;

import fr.thybax.anotherworldplugin.ChatHexa;
import fr.thybax.anotherworldplugin.Informations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAnotherWorld implements CommandExecutor {

    private static String prefix = Informations.getPrefix();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player player) {
            Informations.reload();
            player.sendMessage(ChatHexa.translateColorCodes(prefix + Informations.getError("anotherReload")));

        }
        return false;
    }
}
