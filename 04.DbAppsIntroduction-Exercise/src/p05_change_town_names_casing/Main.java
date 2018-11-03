package p05_change_town_names_casing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Properties credentials = new Properties();
            credentials.setProperty("user", "root");
            credentials.setProperty("password", "");

            Connection connectionToDb = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1/minions_db", credentials
            );

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String countryName = reader.readLine();
            reader.close();

            List<String> towns = new ArrayList<>();
            PreparedStatement sqlRetrieveTowns = connectionToDb.prepareStatement(
                    "SELECT * FROM towns\n" +
                            "WHERE country = ?;" //no need to add '' around ?
            );

            sqlRetrieveTowns.setString(1, countryName);
            ResultSet resultSet = sqlRetrieveTowns.executeQuery();

            while (resultSet.next()) {
                towns.add(resultSet.getString("name").toUpperCase());
            }

            if (towns.isEmpty()) {
                System.out.println("No town names were affected.");
                return;
            }

            PreparedStatement sqlUpdateTownNames = connectionToDb.prepareStatement(
                    "UPDATE towns\n" +
                            "SET name = UPPER(name)\n" +
                            "WHERE country = ?;"
            );
            
            sqlUpdateTownNames.setString(1, countryName);
            sqlUpdateTownNames.executeUpdate();

            System.out.printf("%d town names were affected.%n", towns.size());
            System.out.println(towns);

            connectionToDb.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
