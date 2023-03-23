package fr.thybax.anotherworldplugin;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.UUID;

public class Informations {
    private static String prefix;
    private static String basicError;
    private static FileConfiguration fileConfig;

    private static HashMap<UUID, Double> playerMoney;
    private static HashMap<UUID, Double> playerBMoney;
    private static HashMap<UUID, Double> playerEMoney;

    public static void init(FileConfiguration file){
        fileConfig = file;
        setPrefix();
        setBasicError();
        setMoneys();
    }

    public static String getError(String message){
        return fileConfig.getString(message);
    }



    public static void setBasicError(){
        basicError = fileConfig.getString("errors.basic-error");
    }

    public static String getBasicError(){
        return basicError;
    }




    public static void setPrefix(){
        prefix = fileConfig.getString("prefix");
    }
    public static String getPrefix(){
        return prefix;
    }


    public static void setMoneys(){
        playerMoney = new HashMap<>();
        playerBMoney = new HashMap<>();
        playerEMoney = new HashMap<>();
    }

    public static HashMap<UUID, Double> getPlayerMoney() {
        return playerMoney;
    }

    public static HashMap<UUID, Double> getPlayerBMoney() {
        return playerBMoney;
    }

    public static HashMap<UUID, Double> getPlayerEMoney() {
        return playerEMoney;
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
