package p08_increase_minions_age;

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

        Connection connectionToDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/minions_db", credentials
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String inputNumbers = reader.readLine();
        inputNumbers = inputNumbers.replace(" ", ", "); // 1 2 3 --> 1, 2, 3
        String condition = "id IN(" + inputNumbers + ")"; // id IN(1, 2, 3)

        PreparedStatement sqlUpdateMinionsAge = connectionToDb.prepareStatement(
                String.format("UPDATE minions\n" +
                        "SET age = age + 1, name = CONCAT(UPPER(LEFT(name, 1)), SUBSTR(name, 2))\n" +
                        "WHERE %s", condition)
        );
        sqlUpdateMinionsAge.executeUpdate();

        connectionToDb.close();
    }
}

/*
If I try to write the program like UPDATE minions ... WHERE id IN (?)
and integers need to be put, then sql.Array will NOT work, mysql does not support it.
 */