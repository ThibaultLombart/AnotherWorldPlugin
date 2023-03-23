package fr.thybax.anotherworldplugin.database;

import java.sql.SQLException;

public class DatabaseManager {

    private DbConnection dbConnection;

    public DatabaseManager(){
        this.dbConnection = new DbConnection(new DbCredentials("localhost", "anotherworlduser", ")i08MyYR3ZI8whll", "anotherworld", 3306));
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
