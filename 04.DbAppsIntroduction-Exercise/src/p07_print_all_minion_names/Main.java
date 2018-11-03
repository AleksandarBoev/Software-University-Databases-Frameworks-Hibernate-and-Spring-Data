package p07_print_all_minion_names;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

        List<String> minionNames = new ArrayList<>(60);

        ResultSet minionNamesResultSet = sqlGetAllMinionNames.executeQuery();
        while (minionNamesResultSet.next()) {
            minionNames.add(minionNamesResultSet.getString("name"));
        }
        connectionToDb.close();

        int minionsCount = minionNames.size();

        List<String> minionsReordered = new ArrayList<>();
        for (int i = 0; i < minionsCount / 2; i++) {
            minionsReordered.add(minionNames.get(i));
            minionsReordered.add(minionNames.get(minionsCount - i - 1));
        }

//        if (minionsCount % 2 == 0) {
//            minionsReordered.remove(minionsReordered.size() - 1);
//        }

        for (String minionName : minionsReordered) {
            System.out.println(minionName);
        }


    }
}
