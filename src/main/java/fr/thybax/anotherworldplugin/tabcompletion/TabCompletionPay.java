package fr.thybax.anotherworldplugin.tabcompletion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompletionPay implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {

        if(args.length == 1){
            List<String> moneysToSend = new ArrayList<>();
            moneysToSend.add("Money");moneysToSend.add("AnotherCoins");moneysToSend.add("EventCoins");
            return moneysToSend;
        } else if (args.length == 2){
            List<String> playersName = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for(int i = 0; i < players.length; i++){
                playersName.add(players[i].getName());
            }
            return playersName;
        }

        return new ArrayList<>();
    }
}
