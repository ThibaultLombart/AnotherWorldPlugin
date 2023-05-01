package fr.thybax.anotherworldplugin.tabcompletion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletionGiveItem implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {

        if(args.length == 1){
            List<String> itemToSend = new ArrayList<>();
            itemToSend.add("infinitelavabucket");itemToSend.add("infinitewaterbucket");itemToSend.add("infiniteemptybucket");
            return itemToSend;
        }

        return new ArrayList<>();
    }
}
