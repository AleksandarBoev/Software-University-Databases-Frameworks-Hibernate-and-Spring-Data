package p07_print_all_minion_names;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connectionToDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/minions_db", credentials
        );

        PreparedStatement sqlGetAllMinionNames = connectionToDb.prepareStatement(
            "SELECT name FROM minions"
        );

        Deque<String> minionNames = new ArrayDeque<>(60);

        ResultSet minionNamesResultSet = sqlGetAllMinionNames.executeQuery();
        while (minionNamesResultSet.next()) {
            minionNames.add(minionNamesResultSet.getString("name"));
        }
        connectionToDb.close();

        boolean flag = true;
        while (!minionNames.isEmpty()) {
            if (flag)
                System.out.println(minionNames.pollFirst()); //gets and removes first element
             else
                System.out.println(minionNames.pollLast()); //gets and removes last element

            flag = !flag;
        }

    }
}
