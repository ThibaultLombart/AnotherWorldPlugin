package fr.thybax.anotherworldplugin.database;

import fr.thybax.anotherworldplugin.Exceptions.SqlErrorException;
import fr.thybax.anotherworldplugin.Informations;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final DbConnection dbConnection;

    public DatabaseManager(){
        this.dbConnection = new DbConnection(new DbCredentials(Informations.getError("database.host"), Informations.getError("database.user"), Informations.getError("database.password"), Informations.getError("database.dbName"), Informations.getErrorInt("database.port")));
        checkExistingTables();
    }

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void checkExistingTables(){
        try(Connection connection = dbConnection.getConnection();) {
            Statement statement = connection.createStatement();
            String player = "CREATE TABLE IF NOT EXISTS player"+
                    "(UUID VARCHAR(128) not null," +
                    "NICKNAME VARCHAR(16) not null," +
                    "MONEY double not null," +
                    "ANOTHERCOINS double not null," +
                    "EVENTCOINS double not null," +
                    "Created_at timestamp not null," +
                    "Primary Key (uuid))";
            statement.execute(player);

            String cheque = "CREATE TABLE IF NOT EXISTS cheque"+
                    "(id serial," +
                    "UUID VARCHAR(128) not null," +
                    "NICKNAME VARCHAR(16) not null," +
                    "type VARCHAR(12) not null," +
                    "somme double not null," +
                    "Created_at timestamp not null," +
                    "used int not null DEFAULT 0 ," +
                    "Primary Key (id))";
            statement.execute(cheque);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        try {
            this.dbConnection.close();
        } catch (SQLException e) {
            throw new SqlErrorException(e.toString());
        }
    }
}
