package p06_remove_villain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connectionToMinionsDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/minions_db", credentials
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer villainId = Integer.parseInt(reader.readLine());
        reader.close();

        if (!recordExists(villainId, "villains", connectionToMinionsDb)) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = getName(villainId, "villains", connectionToMinionsDb);
        int minionsCount = getNumberOfMinions(villainId, connectionToMinionsDb);

        releaseMinions(villainId, connectionToMinionsDb);
        deleteRecord(villainId, "villains", connectionToMinionsDb);

        System.out.printf("%s was deleted%n%d minions released", villainName, minionsCount);
    }

    private static boolean recordExists(Integer id, String tableName, Connection connectionToDb) throws SQLException {
        String sqlQuery = String.format("SELECT * FROM %s WHERE id = %d", tableName, id);
        PreparedStatement preparedStatement = connectionToDb.prepareStatement(sqlQuery);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private static String getName(Integer id, String tableName, Connection connectionToDb) throws SQLException {
        String sqlQuery =
                String.format("SELECT * FROM %s WHERE id = %d", tableName, id);

        PreparedStatement retrieveStatement = connectionToDb.prepareStatement(sqlQuery);

        ResultSet resultSet = retrieveStatement.executeQuery();
        resultSet.next();

        return resultSet.getString("name");
    }

    private static int getNumberOfMinions(Integer villainId, Connection connectionToMinionsDb) throws SQLException {
        String sqlQuery = String.format(
                "SELECT COUNT(*) AS minions_count FROM minions_villains WHERE villain_id = %d", villainId);
        PreparedStatement preparedStatement = connectionToMinionsDb.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        return resultSet.getInt("minions_count");
    }

    private static void deleteRecord(Integer id, String tableName, Connection connectionToDb) throws SQLException {
        String sqlQuery = String.format("DELETE FROM %s WHERE id = %d", tableName, id);
        PreparedStatement preparedStatement = connectionToDb.prepareStatement(sqlQuery);

        preparedStatement.executeUpdate();
    }

    private static void releaseMinions(Integer villainId, Connection connectionToMinionsDb) throws SQLException {
        String sqlQuery = "DELETE FROM minions_villains WHERE villain_id = ?";
        PreparedStatement preparedStatement = connectionToMinionsDb.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, villainId);
        preparedStatement.executeUpdate();
    }
}
