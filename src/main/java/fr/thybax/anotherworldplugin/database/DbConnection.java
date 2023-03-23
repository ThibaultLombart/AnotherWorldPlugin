package fr.thybax.anotherworldplugin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DbConnection {

    private DbCredentials dbCredentials;
    private Connection connection;

    public DbConnection(DbCredentials dbCredentials){
        this.dbCredentials = dbCredentials;
        this.connect();
    }

    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.dbCredentials.toURI(),this.dbCredentials.getUser(), this.dbCredentials.getPass());

            Logger.getLogger("Minecraft").info("Connexion réussi a DB.");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws SQLException {
        if(this.connection != null){
            if(!this.connection.isClosed()){
                this.connection.close();
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if(this.connection != null){
            if(!this.connection.isClosed()){
                return this.connection;
            }
        }
        connect();
        return this.connection;
    }
}
