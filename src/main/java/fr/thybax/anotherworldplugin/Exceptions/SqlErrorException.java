package fr.thybax.anotherworldplugin.Exceptions;

public class SqlErrorException extends RuntimeException{
    public SqlErrorException(String preparedStatement){
        super("Error during the SQL Query : "+preparedStatement);
    }
}
