package p03_get_minions_names;

import p03_get_minions_names.contracts.Minion;
import p03_get_minions_names.contracts.Villain;
import p03_get_minions_names.entities.minions.MinionImpl;
import p03_get_minions_names.entities.villains.VillainImpl;

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

        PreparedStatement sqlQuery = connectionToMinionsDb.prepareStatement(
            "SELECT\n" +
                    "  v.id,\n" +
                    "  v.name,\n" +
                    "  m.name,\n" +
                    "  m.age\n" +
                    "FROM villains v\n" +
                    "LEFT JOIN minions_villains mv\n" +
                    "  ON v.id = mv.villain_id\n" +
                    "LEFT JOIN minions m\n" +
                    "  ON m.id = mv.minion_id\n" +
                    "WHERE v.id = ?"
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer villainId = Integer.parseInt(reader.readLine()); //villain with id 6 has no minions
        reader.close();
        sqlQuery.setInt(1, villainId);

        ResultSet resultSet = sqlQuery.executeQuery();
        Villain villain = null;
        while (resultSet.next()) {
            if (villain == null) {
                villain = new VillainImpl(resultSet.getString("v.name"));
            }

            Minion currentMinion = new MinionImpl(
                    resultSet.getString("m.name"),
                    resultSet.getInt("m.age")
            );

            if (resultSet.wasNull())  //Reports whether the last column read had a value of SQL NULL.
                continue;

            villain.addMinion(currentMinion);
        }
        connectionToMinionsDb.close();
        if (villain == null) {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
        } else {
            System.out.print(villain);
        }

    }
}
