package p08_increase_minions_age;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connectionToDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/minions_db", credentials
        );

        PreparedStatement sqlUpdateMinionsAge = connectionToDb.prepareStatement(
                "UPDATE minions\n" +
                        "SET age = age + 1, name = CONCAT(UPPER(LEFT(name, 1)), SUBSTR(name, 2))\n" +
                        "WHERE id = ?"
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        reader.close();

        Integer[] ids = Arrays.stream(input.split(" "))
                .map(x -> Integer.parseInt(x))
                .toArray(n -> new Integer[n]);

        for (Integer number : ids) {
            sqlUpdateMinionsAge.setInt(1, number);
            sqlUpdateMinionsAge.executeUpdate();
        }

        connectionToDb.close();
    }
}

/*
If I try to write the program like UPDATE minions ... WHERE id IN (?)
and integers need to be put then sql.Array will NOT work, because of mysql.
Doesn't work even if I put a String in there. Also doesn't work this way:
… WHERE ? and putting the string “id = 1 OR id = 2 OR id = 3”.
The way I have done it is unpractical and needs multiple queries.
 */