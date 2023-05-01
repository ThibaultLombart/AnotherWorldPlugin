package fr.thybax.anotherworldplugin.database;

import fr.thybax.anotherworldplugin.Informations;

import java.sql.SQLException;

public class DatabaseManager {

    private DbConnection dbConnection;

    public DatabaseManager(){
        this.dbConnection = new DbConnection(new DbCredentials(Informations.getError("database.host"), Informations.getError("database.user"), Informations.getError("database.password"), Informations.getError("database.dbName"), Informations.getErrorInt("database.port")));
    }

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void close(){
        try {
            this.dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
