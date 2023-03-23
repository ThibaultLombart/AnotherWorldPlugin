package fr.thybax.anotherworldplugin;

import fr.thybax.anotherworldplugin.database.DbConnection;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;
import java.util.UUID;

public class ListenersJoin implements Listener {

    private Main main;
    private final static double ON_CONNECTION_MONEY = 500;
    private final static double ON_CONNECTION_BMONEY = 0;
    private final static double ON_CONNECTION_EMONEY = 0;

    public ListenersJoin(Main main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        final String pseudo = event.getPlayer().getName();

        // Si le serveur n'a pas déjà en cache l'uuid dans le hashmap PlayerMoney, il execute
        if(Informations.getPlayerMoney() == null || !Informations.getPlayerMoney().containsKey(uuid)) {


            final DbConnection databaseManager = main.getDatabaseManager().getDbConnection();
            try {
                final Connection connection = databaseManager.getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player WHERE UUID = ?");

                preparedStatement.setString(1,uuid.toString());
                final ResultSet resultSet = preparedStatement.executeQuery();


                // Traitement du résultat.
                if(resultSet.next()){
                    final String nickname = resultSet.getString("NICKNAME");
                    if(!nickname.equals(pseudo)){
                        changeNickname(connection, uuid, pseudo);
                    }

                    final double money = resultSet.getDouble("MONEY");
                    final double bMoney = resultSet.getDouble("BOUTIQUECOINS");
                    final double eMoney = resultSet.getDouble("EVENTCOINS");

                    Informations.getPlayerMoney().put(uuid, money);
                    Informations.getPlayerBMoney().put(uuid, bMoney);
                    Informations.getPlayerEMoney().put(uuid, eMoney);


                } else {
                    createUser(connection, uuid, pseudo);
                    Bukkit.broadcastMessage(ChatHexa.translateColorCodes(Informations.getPrefix() + Informations.getError("welcome1") + pseudo + Informations.getError("welcome2")));
                }

                databaseManager.close();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void createUser(Connection connection, UUID uuid,String pseudo){
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player VALUES (?,?,?,?,?,?)");


            final long time = System.currentTimeMillis();
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2,pseudo);
            preparedStatement.setDouble(3,ON_CONNECTION_MONEY);
            preparedStatement.setDouble(4,ON_CONNECTION_BMONEY);
            preparedStatement.setDouble(5,ON_CONNECTION_EMONEY);
            preparedStatement.setTimestamp(6, new Timestamp(time));
            preparedStatement.executeUpdate();

            Informations.getPlayerMoney().put(uuid, ON_CONNECTION_MONEY);
            Informations.getPlayerBMoney().put(uuid, ON_CONNECTION_BMONEY);
            Informations.getPlayerEMoney().put(uuid, ON_CONNECTION_EMONEY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeNickname(Connection connection, UUID uuid,String pseudo){
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET NICKNAME = ? WHERE UUID = ?");
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.setString(1,pseudo);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
