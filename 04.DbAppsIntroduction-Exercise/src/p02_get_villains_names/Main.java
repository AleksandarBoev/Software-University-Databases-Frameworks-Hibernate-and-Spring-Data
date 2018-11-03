package p02_get_villains_names;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connectionToMinionsDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/minions_db", credentials
        );

        PreparedStatement sqlQuery = connectionToMinionsDb.prepareStatement(
                "SELECT\n" +
                        "  v.name AS 'villain_name',\n" +
                        "  COUNT(m.id) AS 'minions_count'\n" +
                        "FROM villains v\n" +
                        "INNER JOIN minions_villains mv\n" +
                        "ON v.id = mv.villain_id\n" +
                        "LEFT JOIN minions m\n" +
                        "ON m.id = mv.minion_id\n" +
                        "GROUP BY v.id\n" +
                        "HAVING minions_count > 3\n" +
                        "ORDER BY minions_count DESC"
        );

        ResultSet resultSet = sqlQuery.executeQuery();
        while (resultSet.next()) {
            System.out.printf("%s %d%n",
                    resultSet.getString("villain_name"),
                    resultSet.getInt("minions_count"));
        }
        connectionToMinionsDb.close();
    }
}
