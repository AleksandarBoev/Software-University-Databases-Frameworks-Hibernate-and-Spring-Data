package p09_increase_age_stored_procedure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

/*
Stored procedure code:
DELIMITER $$
CREATE PROCEDURE usp_get_older(minionId INT)
BEGIN
  UPDATE minions
  SET age = age + 1
  WHERE id = minionId;
END$$
 */

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connectionToDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/minions_db", credentials
        );

        PreparedStatement sqlCallProcedure = connectionToDb.prepareStatement(
            "CALL usp_get_older(?)"
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer minionId = Integer.parseInt(reader.readLine());
        reader.close();

        sqlCallProcedure.setInt(1, minionId);
        sqlCallProcedure.execute();

        PreparedStatement sqlGetCertainMinion = connectionToDb.prepareStatement(
                "SELECT name, age FROM minions WHERE id = ?"
        );
        sqlGetCertainMinion.setInt(1, minionId);
        ResultSet resultSet = sqlGetCertainMinion.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d", resultSet.getString("name"), resultSet.getInt("age"));
        }

        connectionToDb.close();
    }
}
