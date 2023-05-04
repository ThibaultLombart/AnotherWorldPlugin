package fr.thybax.anotherworldplugin;

import fr.thybax.anotherworldplugin.Exceptions.SqlErrorException;
import fr.thybax.anotherworldplugin.database.DbConnection;
import fr.thybax.anotherworldplugin.economy.DatabaseController;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Informations {
    private static String prefix;

    private static Main main;
    private static String basicError;
    private static FileConfiguration fileConfig;

    private static HashMap<UUID, Double> playerMoney;
    private static HashMap<UUID, Double> playerBMoney;
    private static HashMap<UUID, Double> playerEMoney;

    private static String moneyName = "MONEY";
    private static String anotherCoinsName = "ANOTHERCOINS";
    private static String eventCoinsName = "EVENTCOINS";


    // ------------------------------------------------------- CONFIGURATIONS ------------------------------------------------------- \\
    private Informations() {
        throw new IllegalStateException("Utility class");
    }

    public static void init(FileConfiguration file){
        fileConfig = file;
        setPrefix();
        setBasicError();
        setMoneys();
    }


    public static void reload(){
        fileConfig = main.getConfig();
    }

    public static void initMain(Main mainRequest){
        main = mainRequest;
        initPlayersOnline();
    }

    // ------------------------------------------------------- Informations to Get ------------------------------------------------------- \\

    public static String getError(String message){
        return fileConfig.getString(message);
    }

    public static int getErrorInt(String message){
        return fileConfig.getInt(message);
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

    public static Map<UUID, Double> getPlayerMoney() {
        return playerMoney;
    }

    public static Map<UUID, Double> getPlayerBMoney() {
        return playerBMoney;
    }

    public static Map<UUID, Double> getPlayerEMoney() {
        return playerEMoney;
    }


    // ------------------------------------------------------- Utils ------------------------------------------------------- \\
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static void  initPlayersOnline(){
        final DbConnection databaseManager = main.getDatabaseManager().getDbConnection();
        Bukkit.getScheduler().runTaskAsynchronously(main,()-> {
            try {
                final Connection connection = databaseManager.getConnection();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (int i = 0; i < players.length; i++) {
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player WHERE UUID = ?");
                    UUID uuid = players[i].getUniqueId();

                    preparedStatement.setString(1, uuid.toString());
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    // Traitement du résultat.
                    if (resultSet.next()) {

                        final double money = resultSet.getDouble(moneyName);
                        final double aMoney = resultSet.getDouble(anotherCoinsName);
                        final double eMoney = resultSet.getDouble(eventCoinsName);

                        Informations.getPlayerMoney().put(uuid, money);
                        Informations.getPlayerBMoney().put(uuid, aMoney);
                        Informations.getPlayerEMoney().put(uuid, eMoney);
                    }
                }

            } catch (Exception e) {
                throw new SqlErrorException("SELECT * FROM player WHERE UUID = ?     Informations");
            }
        });
    }


    public static boolean addMoney(UUID uuid, double somme, String typeMoney){
        if (typeMoney.equalsIgnoreCase(moneyName) || typeMoney.equalsIgnoreCase(anotherCoinsName) || typeMoney.equalsIgnoreCase(eventCoinsName)) {
            if(typeMoney.equalsIgnoreCase(moneyName)){
                double money = playerMoney.get(uuid);
                playerMoney.put(uuid,money+somme);
            } else if (typeMoney.equalsIgnoreCase(anotherCoinsName)){
                double money = playerBMoney.get(uuid);
                playerBMoney.put(uuid,money+somme);
            } else if (typeMoney.equalsIgnoreCase(eventCoinsName)){
                double money = playerEMoney.get(uuid);
                playerEMoney.put(uuid,money+somme);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public static boolean removeMoney(UUID uuid, double somme, String typeMoney){
        if (typeMoney.equalsIgnoreCase(moneyName) || typeMoney.equalsIgnoreCase(anotherCoinsName) || typeMoney.equalsIgnoreCase(eventCoinsName)) {
            if(typeMoney.equalsIgnoreCase(moneyName)){
                double money = playerMoney.get(uuid);
                playerMoney.put(uuid,money-somme);
            } else if (typeMoney.equalsIgnoreCase(anotherCoinsName)){
                double money = playerBMoney.get(uuid);
                playerBMoney.put(uuid,money-somme);
            } else if (typeMoney.equalsIgnoreCase(eventCoinsName)){
                double money = playerEMoney.get(uuid);
                playerEMoney.put(uuid,money-somme);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    public static void saveAll(){
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for(int i = 0; i < players.length; i++){
            DatabaseController.savePlayer(players[i].getUniqueId());
        }
    }

}
