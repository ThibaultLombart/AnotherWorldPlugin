package fr.thybax.anotherworldplugin;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHexa {
    static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    /**
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    public static String translateColorCodes(String text){

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++){
            if (texts[i].equalsIgnoreCase("&")){
                //get the next string
                i++;
                if (texts[i].charAt(0) == '#'){
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)) + texts[i].substring(7));
                }else{
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            }else{
                finalText.append(texts[i]);
            }
        }

        return finalText.toString();
    }

    public static TextComponent translateColorCodesToTextComponent(String text){

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        ComponentBuilder builder = new ComponentBuilder();

        for (int i = 0; i < texts.length; i++){
            TextComponent subComponent = new TextComponent();
            if (texts[i].equalsIgnoreCase("&")){
                //get the next string
                i++;
                if (texts[i].charAt(0) == '#'){
                    subComponent.setText(texts[i].substring(7));
                    subComponent.setColor(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)));
                    builder.append(subComponent);
                }else{
                    if (texts[i].length() > 1){
                        subComponent.setText(texts[i].substring(1));
                    }else{
                        subComponent.setText(" ");
                    }

                    switch (texts[i].charAt(0)){
                        case '0':
                            subComponent.setColor(ChatColor.BLACK.asBungee());
                            break;
                        case '1':
                            subComponent.setColor(ChatColor.DARK_BLUE.asBungee());
                            break;
                        case '2':
                            subComponent.setColor(ChatColor.DARK_GREEN.asBungee());
                            break;
                        case '3':
                            subComponent.setColor(ChatColor.DARK_AQUA.asBungee());
                            break;
                        case '4':
                            subComponent.setColor(ChatColor.DARK_RED.asBungee());
                            break;
                        case '5':
                            subComponent.setColor(ChatColor.DARK_PURPLE.asBungee());
                            break;
                        case '6':
                            subComponent.setColor(ChatColor.GOLD.asBungee());
                            break;
                        case '7':
                            subComponent.setColor(ChatColor.GRAY.asBungee());
                            break;
                        case '8':
                            subComponent.setColor(ChatColor.DARK_GRAY.asBungee());
                            break;
                        case '9':
                            subComponent.setColor(ChatColor.BLUE.asBungee());
                            break;
                        case 'a':
                            subComponent.setColor(ChatColor.GREEN.asBungee());
                            break;
                        case 'b':
                            subComponent.setColor(ChatColor.AQUA.asBungee());
                            break;
                        case 'c':
                            subComponent.setColor(ChatColor.RED.asBungee());
                            break;
                        case 'd':
                            subComponent.setColor(ChatColor.LIGHT_PURPLE.asBungee());
                            break;
                        case 'e':
                            subComponent.setColor(ChatColor.YELLOW.asBungee());
                            break;
                        case 'f':
                            subComponent.setColor(ChatColor.WHITE.asBungee());
                            break;
                        case 'k':
                            subComponent.setObfuscated(true);
                            break;
                        case 'l':
                            subComponent.setBold(true);
                            break;
                        case 'm':
                            subComponent.setStrikethrough(true);
                            break;
                        case 'n':
                            subComponent.setUnderlined(true);
                            break;
                        case 'o':
                            subComponent.setItalic(true);
                            break;
                        case 'r':
                            subComponent.setColor(ChatColor.RESET.asBungee());
                            break;
                    }

                    builder.append(subComponent);
                }
            }else{
                builder.append(texts[i]);
            }
        }

        return new TextComponent(builder.create());

    }

}
