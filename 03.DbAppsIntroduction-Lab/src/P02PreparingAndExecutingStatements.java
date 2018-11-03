import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P02PreparingAndExecutingStatements {
    public static void main(String[] args) throws SQLException, IOException {
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connectionToDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/diablo", credentials
        );

        PreparedStatement sqlQuery = connectionToDb.prepareStatement(
                "SELECT\n" +
                        "  u.first_name,\n" +
                        "  u.last_name,\n" +
                        "  COUNT(ug.id) AS 'games_played'\n" +
                        "FROM users_games ug\n" +
                        "INNER JOIN users u\n" +
                        "  ON ug.user_id = u.id\n" +
                        "WHERE u.last_name = ?\n" +
                        "GROUP BY u.id\n" +
                        "ORDER BY u.id ASC"
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String playerName = reader.readLine();
        reader.close();

        sqlQuery.setString(1, playerName);
        ResultSet resultSet = sqlQuery.executeQuery();

        boolean atLeastOneResult = false;
        while (resultSet.next()) {
            atLeastOneResult = true;
            System.out.println("User: " + playerName);
            System.out.printf("%s %s has played %d games",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("games_played")
            );
        }

        if (!atLeastOneResult)
            System.out.println("No such user exists");

        connectionToDb.close();
    }
}
