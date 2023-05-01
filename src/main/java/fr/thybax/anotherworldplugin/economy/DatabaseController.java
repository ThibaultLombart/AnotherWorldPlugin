package fr.thybax.anotherworldplugin.economy;

import fr.thybax.anotherworldplugin.Informations;
import fr.thybax.anotherworldplugin.Main;
import fr.thybax.anotherworldplugin.database.DbConnection;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class DatabaseController {

    public static Main mainItself;

    public static void init(Main main) {
        mainItself = main;
    }

    public static int ajouterCheque(UUID uuid,String pseudo, String typeMonnaie, double somme){
        if (typeMonnaie.equalsIgnoreCase("MONEY") || typeMonnaie.equalsIgnoreCase("ANOTHERCOINS") || typeMonnaie.equalsIgnoreCase("EVENTCOINS")) {
            final DbConnection databaseManager = mainItself.getDatabaseManager().getDbConnection();
            try{
                Connection connection = databaseManager.getConnection();
                final Timestamp time = new Timestamp(System.currentTimeMillis());
                final PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO cheque(pseudo,uuid,type,somme,Created_at) VALUES (?,?,?,?,?)");
                preparedStatement2.setString(1,pseudo);
                preparedStatement2.setString(2,uuid.toString());
                preparedStatement2.setString(3,typeMonnaie);
                preparedStatement2.setDouble(4,somme);
                preparedStatement2.setTimestamp(5,time);
                preparedStatement2.executeUpdate();

                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM cheque where Created_at = ?");
                preparedStatement.setTimestamp(1,time);
                final ResultSet resultSet = preparedStatement.executeQuery();
                int id = -1;
                if(resultSet.next()){
                    id = resultSet.getInt(1);
                }


                return id;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return -1;
    }

    public static Boolean retirerArgent(UUID uuid, double somme, String typeMonnaie){
            if (typeMonnaie.equalsIgnoreCase("MONEY") || typeMonnaie.equalsIgnoreCase("ANOTHERCOINS") || typeMonnaie.equalsIgnoreCase("EVENTCOINS")) {
                final DbConnection databaseManager = mainItself.getDatabaseManager().getDbConnection();
                try {
                    Connection connection = databaseManager.getConnection();
                    if (typeMonnaie.equalsIgnoreCase("MONEY")) {
                        final PreparedStatement preparedStatement = connection.prepareStatement("SELECT MONEY FROM player WHERE UUID = ?");
                        preparedStatement.setString(1, uuid.toString());
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            somme = resultSet.getDouble(typeMonnaie) - somme;


                            final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET MONEY=? WHERE UUID = ?");
                            preparedStatement2.setDouble(1, somme);
                            preparedStatement2.setString(2, uuid.toString());
                            preparedStatement2.executeUpdate();

                            Informations.getPlayerMoney().put(uuid, somme);
                        }


                    } else if (typeMonnaie.equalsIgnoreCase("ANOTHERCOINS")) {
                        final PreparedStatement preparedStatement = connection.prepareStatement("SELECT ANOTHERCOINS FROM player WHERE UUID = ?");
                        preparedStatement.setString(1, uuid.toString());
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            somme = resultSet.getDouble(typeMonnaie) - somme;


                            final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET ANOTHERCOINS=? WHERE UUID = ?");
                            preparedStatement2.setDouble(1, somme);
                            preparedStatement2.setString(2, uuid.toString());
                            preparedStatement2.executeUpdate();

                            Informations.getPlayerBMoney().put(uuid, somme);
                        }


                    } else {
                        final PreparedStatement preparedStatement = connection.prepareStatement("SELECT EVENTCOINS FROM player WHERE UUID = ?");
                        preparedStatement.setString(1, uuid.toString());

                        final ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            somme = resultSet.getDouble(typeMonnaie) - somme;


                            final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET EVENTCOINS=? WHERE UUID = ?");
                            preparedStatement2.setDouble(1, somme);
                            preparedStatement2.setString(2, uuid.toString());
                            preparedStatement2.executeUpdate();

                            Informations.getPlayerEMoney().put(uuid, somme);
                        }
                    }


                    databaseManager.close();


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return false;
            }

            return true;
    }

    public static Boolean ajouterArgent(UUID uuid, double somme, String typeMonnaie) {

        if (typeMonnaie.equalsIgnoreCase("MONEY") || typeMonnaie.equalsIgnoreCase("ANOTHERCOINS") || typeMonnaie.equalsIgnoreCase("EVENTCOINS")) {
            final DbConnection databaseManager = mainItself.getDatabaseManager().getDbConnection();
            try {
                Connection connection = databaseManager.getConnection();
                if (typeMonnaie.equalsIgnoreCase("MONEY")) {
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT MONEY FROM player WHERE UUID = ?");
                    preparedStatement.setString(1, uuid.toString());
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        somme += resultSet.getDouble(typeMonnaie);


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET MONEY=? WHERE UUID = ?");
                        preparedStatement2.setDouble(1, somme);
                        preparedStatement2.setString(2, uuid.toString());
                        preparedStatement2.executeUpdate();

                        Informations.getPlayerMoney().put(uuid, somme);
                    }


                } else if (typeMonnaie.equalsIgnoreCase("ANOTHERCOINS")) {
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT ANOTHERCOINS FROM player WHERE UUID = ?");
                    preparedStatement.setString(1, uuid.toString());
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        somme += resultSet.getDouble(typeMonnaie);


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET ANOTHERCOINS=? WHERE UUID = ?");
                        preparedStatement2.setDouble(1, somme);
                        preparedStatement2.setString(2, uuid.toString());
                        preparedStatement2.executeUpdate();

                        Informations.getPlayerBMoney().put(uuid, somme);
                    }


                } else {
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT EVENTCOINS FROM player WHERE UUID = ?");
                    preparedStatement.setString(1, uuid.toString());

                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        somme += resultSet.getDouble(typeMonnaie);


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET EVENTCOINS=? WHERE UUID = ?");
                        preparedStatement2.setDouble(1, somme);
                        preparedStatement2.setString(2, uuid.toString());
                        preparedStatement2.executeUpdate();

                        Informations.getPlayerEMoney().put(uuid, somme);
                    }
                }


                databaseManager.close();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }

        return true;
    }

}
