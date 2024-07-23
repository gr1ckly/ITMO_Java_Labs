package model;

import org.example.Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.Properties;

public class SQLConnection {
    private static Connection connection;
    private static Connection createConnection() throws IOException, SQLException {
        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("DBconfig.properties"));
        return DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
    }
    public static Connection getConnection() throws IOException, SQLException {
        if (SQLConnection.connection == null){
            SQLConnection.connection = SQLConnection.createConnection();
        }
        return SQLConnection.connection;
    }

    public static void close() throws SQLException {
        if (SQLConnection.connection != null){
            SQLConnection.connection.close();
        }
    }
}
