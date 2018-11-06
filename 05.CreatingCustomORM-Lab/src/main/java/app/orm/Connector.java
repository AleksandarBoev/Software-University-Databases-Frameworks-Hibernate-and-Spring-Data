package app.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Connection connectionToDb;

    public static void createConnection(String userName, String password, String dbName) throws SQLException {
        Properties credentials = new Properties();
        credentials.setProperty("user", userName);
        credentials.setProperty("password", password);

        String sslConnectionErrorFix = "?autoReconnect=true&useSSL=false";

        String connectionInfo = "jdbc:mysql://127.0.0.1/" + dbName + "" + sslConnectionErrorFix;
        connectionToDb = DriverManager.getConnection(connectionInfo, credentials);
    }

    public static Connection getConnection() {
        return connectionToDb;
    }
}
