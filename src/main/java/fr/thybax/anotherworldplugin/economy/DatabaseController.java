package fr.thybax.anotherworldplugin.economy;

import fr.thybax.anotherworldplugin.Informations;
import fr.thybax.anotherworldplugin.Main;
import fr.thybax.anotherworldplugin.database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseController {

    public static Main mainItself;

    public static void init(Main main) {
        mainItself = main;
    }

    public static Boolean retirerArgent(UUID uuid, double somme, String typeMonnaie){

        if(typeMonnaie.equalsIgnoreCase("MONEY") || typeMonnaie.equalsIgnoreCase("BOUTIQUECOINS") || typeMonnaie.equalsIgnoreCase("EVENTCOINS")){
            final DbConnection databaseManager = mainItself.getDatabaseManager().getDbConnection();
            try {
                Connection connection = databaseManager.getConnection();
                if(typeMonnaie.equalsIgnoreCase("MONEY")){
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT MONEY FROM player WHERE UUID = ?");
                    preparedStatement.setString(1,uuid.toString());
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        somme = resultSet.getDouble(typeMonnaie) - somme;


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET MONEY=? WHERE UUID = ?");
                        preparedStatement2.setDouble(1, somme);
                        preparedStatement2.setString(2, uuid.toString());
                        preparedStatement2.executeUpdate();

                        Informations.getPlayerMoney().put(uuid,somme);
                    }


                } else if (typeMonnaie.equalsIgnoreCase("BOUTIQUECOINS")) {
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT BOUTIQUECOINS FROM player WHERE UUID = ?");
                    preparedStatement.setString(1,uuid.toString());
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        somme = resultSet.getDouble(typeMonnaie) - somme;


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET BOUTIQUECOINS=? WHERE UUID = ?");
                        preparedStatement2.setDouble(1, somme);
                        preparedStatement2.setString(2, uuid.toString());
                        preparedStatement2.executeUpdate();

                        Informations.getPlayerBMoney().put(uuid,somme);
                    }


                } else{
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT EVENTCOINS FROM player WHERE UUID = ?");
                    preparedStatement.setString(1,uuid.toString());

                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        somme = resultSet.getDouble(typeMonnaie) - somme;


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET BOUTIQUECOINS=? WHERE UUID = ?");
                        preparedStatement2.setDouble(1, somme);
                        preparedStatement2.setString(2, uuid.toString());
                        preparedStatement2.executeUpdate();

                        Informations.getPlayerEMoney().put(uuid,somme);
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

        if (typeMonnaie.equalsIgnoreCase("MONEY") || typeMonnaie.equalsIgnoreCase("BOUTIQUECOINS") || typeMonnaie.equalsIgnoreCase("EVENTCOINS")) {
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


                } else if (typeMonnaie.equalsIgnoreCase("BOUTIQUECOINS")) {
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT BOUTIQUECOINS FROM player WHERE UUID = ?");
                    preparedStatement.setString(1, uuid.toString());
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        somme += resultSet.getDouble(typeMonnaie);


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET BOUTIQUECOINS=? WHERE UUID = ?");
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


                        final PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE player SET BOUTIQUECOINS=? WHERE UUID = ?");
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
