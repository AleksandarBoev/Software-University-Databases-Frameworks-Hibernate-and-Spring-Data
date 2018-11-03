package p04_add_minion;

import p04_add_minion.contracts.Minion;
import p04_add_minion.contracts.Villain;
import p04_add_minion.entities.minions.MinionImpl;
import p04_add_minion.entities.villains.VillainImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

/*
        Expected result from queries if database has not been touched:
        Last minion is with id 50, so new minion should be with id 51.
        Last town is with id 5. If a new town is created it should be with id 6.
        Last villain is with id 6. If a new villain is created he should be id id 7.
        Last row of minions_villains should be 51 - 6 OR 51 - 7.

        Why rely on the auto generate for the id? Because someone could have deleted a record,
        but the auto generate wouldn't fill in that spot. And I don't want that spot filled because
        deleted information can be restored and if a new record has the id of the deleted record
        then restoring the deleted record would cause problems.
*/

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        try {
            StringBuilder resultFromProgram = new StringBuilder();

            Properties credentials = new Properties();
            credentials.setProperty("user", "root");
            credentials.setProperty("password", "");

            Connection connectionToMinionsDb = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1/minions_db", credentials
            );

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] minionTokens =
                    reader.readLine().replace("Minion: ", "").split(" ");

            String minionName = minionTokens[0];
            Integer minionAge = Integer.parseInt(minionTokens[1]);
            String townName = minionTokens[2];

            String villainName = reader.readLine().replace("Villain: ", "");
            reader.close();

            PreparedStatement getTownQuery = connectionToMinionsDb.prepareStatement(
                    "SELECT * FROM towns WHERE name = ?"
            );
            getTownQuery.setString(1, townName);

            ResultSet resultSet = getTownQuery.executeQuery();

            Integer townId = null;
            if (resultSet.next()) { //get town id if query has found any information
                townId = resultSet.getInt("id");
            } else { //insert a town and get its auto generated id
                PreparedStatement insertIntoTownQuery = connectionToMinionsDb.prepareStatement(
                        "INSERT INTO towns(name) VALUES(?)"
                );
                insertIntoTownQuery.setString(1, townName);
                insertIntoTownQuery.executeUpdate();

                ResultSet townResultSet = getTownQuery.executeQuery();
                townResultSet.next();
                townId = townResultSet.getInt("id");
                resultFromProgram.append(
                        String.format("Town %s was added to the database.%n", townName));
            }

            addMinionToDb(minionName, minionAge, townId, connectionToMinionsDb);
            Minion minion = getMinionFromDb(minionName, connectionToMinionsDb);

            PreparedStatement villainRetrieveQuery = connectionToMinionsDb.prepareStatement(
                    "SELECT * FROM villains WHERE name = ?;"
            );
            villainRetrieveQuery.setString(1, villainName);

            Integer villainId = null;

            resultSet = villainRetrieveQuery.executeQuery();
            if (resultSet.next()) { //if a villain with this name has been found
                villainId = resultSet.getInt("id");
            } else { //not found -> needs to be added
                addVillainToDb(villainName, connectionToMinionsDb);
                Villain villain = getVillainFromDb(villainName, connectionToMinionsDb);
                villainId = villain.getId();
                resultFromProgram.append(
                        String.format("Villain %s was added to the database.%n", villainName));
            }

            PreparedStatement sqlAddToMinionsVillainsQuery = connectionToMinionsDb.prepareStatement(
                    "INSERT INTO minions_villains(minion_id, villain_id) \n" +
                            "VALUES(?, ?)"
            );
            sqlAddToMinionsVillainsQuery.setInt(1, minion.getId());
            sqlAddToMinionsVillainsQuery.setInt(2, villainId);

            sqlAddToMinionsVillainsQuery.executeUpdate();
            connectionToMinionsDb.close();

            resultFromProgram.append(
                    String.format("Successfully added %s to be minion of %s.%n",
                            minionName, villainName));

            System.out.println(resultFromProgram.toString());
        } catch (SQLException se) {
            se.printStackTrace();
        }


    }

    private static void addMinionToDb(
            String name, Integer age, Integer town_id, Connection connectionToDb) throws SQLException {
        PreparedStatement sqlAddQuery = connectionToDb.prepareStatement(
                "INSERT INTO minions(name, age, town_id)\n" +
                        "VALUES (?, ?, ?)"
        );

        sqlAddQuery.setString(1, name);
        sqlAddQuery.setInt(2, age);
        sqlAddQuery.setInt(3, town_id);

        sqlAddQuery.executeUpdate();
    }

    private static Minion getMinionFromDb(String name, Connection connectionToDb) throws SQLException {
        PreparedStatement sqlRetrieveMinion = connectionToDb.prepareStatement(
                "SELECT * FROM minions\n" +
                        "WHERE name = ?"
        );

        sqlRetrieveMinion.setString(1, name);
        ResultSet resultSet = sqlRetrieveMinion.executeQuery();

        if (resultSet.next()) {
            return new MinionImpl(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getInt("town_id")
            );
        } else {
            return null;
        }
    }

    private static void addVillainToDb(
            String name, Connection connectionToDb) throws SQLException {
        PreparedStatement sqlAddQuery = connectionToDb.prepareStatement(
                "INSERT INTO villains(name, evilness_factor)\n" +
                        "VALUES (?, ?)"
        );

        sqlAddQuery.setString(1, name);
        sqlAddQuery.setString(2, "evil");


        sqlAddQuery.executeUpdate();
    }

    private static Villain getVillainFromDb
            (String name, Connection connectionToDb) throws SQLException {
        PreparedStatement sqlRetrieveVillain = connectionToDb.prepareStatement(
                "SELECT * FROM villains\n" +
                        "WHERE name = ?"
        );

        sqlRetrieveVillain.setString(1, name);
        ResultSet resultSet = sqlRetrieveVillain.executeQuery();

        if (resultSet.next()) {
            return new VillainImpl(
                    resultSet.getInt("id"),
                    resultSet.getString("name"));
        } else {
            return null;
        }
    }
}
