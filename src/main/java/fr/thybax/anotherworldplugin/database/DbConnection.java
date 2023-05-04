package fr.thybax.anotherworldplugin.database;

import fr.thybax.anotherworldplugin.Exceptions.SqlErrorException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        } catch (SQLException | ClassNotFoundException e) {
            throw new SqlErrorException(e.toString());
        }
    }

    public void close() throws SQLException {
        if(this.connection != null && !this.connection.isClosed()){
            this.connection.close();
        }
    }

    public Connection getConnection() throws SQLException {
        if(this.connection != null && !this.connection.isClosed()){
            return this.connection;
        }
        connect();
        return this.connection;
    }
}
