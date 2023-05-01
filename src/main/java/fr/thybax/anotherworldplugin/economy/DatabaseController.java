package fr.thybax.anotherworldplugin.economy;

import fr.thybax.anotherworldplugin.Exceptions.SqlErrorException;
import fr.thybax.anotherworldplugin.Informations;
import fr.thybax.anotherworldplugin.Main;
import fr.thybax.anotherworldplugin.database.DbConnection;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class DatabaseController {

    private static Main mainItself;

    private DatabaseController(){
        throw new IllegalStateException("Utility class");
    }

    public static void init(Main main) {
        mainItself = main;
    }

    public static int ajouterCheque(UUID uuid,String pseudo, String typeMonnaie, double somme){
        if (typeMonnaie.equalsIgnoreCase("MONEY") || typeMonnaie.equalsIgnoreCase("ANOTHERCOINS") || typeMonnaie.equalsIgnoreCase("EVENTCOINS")) {
            final DbConnection databaseManager = mainItself.getDatabaseManager().getDbConnection();
            try (Connection connection = databaseManager.getConnection();final PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO cheque(pseudo,uuid,type,somme,Created_at) VALUES (?,?,?,?,?)");final PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM cheque where Created_at = ?")){

                final Timestamp time = new Timestamp(System.currentTimeMillis());
                preparedStatement2.setString(1,pseudo);
                preparedStatement2.setString(2,uuid.toString());
                preparedStatement2.setString(3,typeMonnaie);
                preparedStatement2.setDouble(4,somme);
                preparedStatement2.setTimestamp(5,time);
                preparedStatement2.executeUpdate();

                preparedStatement.setTimestamp(1,time);
                final ResultSet resultSet = preparedStatement.executeQuery();
                int id = -1;
                if(resultSet.next()){
                    id = resultSet.getInt(1);
                }


                return id;
            } catch (SQLException e) {
                throw new SqlErrorException("Add Cheque ERROR");
            }
        }
        return -1;
    }

    public static void savePlayer(UUID uuid){
        double money = Informations.getPlayerMoney().get(uuid);
        double bMoney = Informations.getPlayerBMoney().get(uuid);
        double eMoney = Informations.getPlayerEMoney().get(uuid);
        final DbConnection databaseManager = mainItself.getDatabaseManager().getDbConnection();
        try (Connection connection = databaseManager.getConnection();final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET money = ?, ANOTHERCOINS = ?, EVENTCOINS = ? where UUID = ?")){

            preparedStatement.setDouble(1,money);
            preparedStatement.setDouble(2,bMoney);
            preparedStatement.setDouble(3,eMoney);
            preparedStatement.setString(4,uuid.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlErrorException("save "+uuid.toString()+" ERROR");
        }
    }


}
