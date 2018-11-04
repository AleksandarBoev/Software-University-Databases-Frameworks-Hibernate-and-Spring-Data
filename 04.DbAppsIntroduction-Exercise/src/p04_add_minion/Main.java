package p04_add_minion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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

        boolean minionAlreadyExists = recordExists(minionName, "minions", connectionToMinionsDb);
        boolean townAlreadyExists = recordExists(townName, "towns", connectionToMinionsDb);
        boolean villainAlreadyExists = recordExists(villainName, "villains", connectionToMinionsDb);

        if (minionAlreadyExists) {
            System.out.println("A minion with this name already exists!");
            return;
        }

        if (!townAlreadyExists) {
            Map<String, String> townInfo = new HashMap<>();
            townInfo.put("name", surroundWithApostrophes(townName));

            insertRecord(townInfo, "towns", connectionToMinionsDb);

            System.out.printf("Town %s was added to the database.%n", townName);
        }

        if (!villainAlreadyExists) {
            Map<String, String> villainInfo = new HashMap<>();
            villainInfo.put("name", surroundWithApostrophes(villainName));
            villainInfo.put("evilness_factor", surroundWithApostrophes("evil"));

            insertRecord(villainInfo, "villains", connectionToMinionsDb);

            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        int townId = getId(townName, "towns", connectionToMinionsDb);
        int villainId = getId(villainName, "villains", connectionToMinionsDb);

        Map<String, String> minionInfo = new HashMap<>();
        minionInfo.put("name", surroundWithApostrophes(minionName));
        minionInfo.put("age", "" + minionAge);
        minionInfo.put("town_id", "" + townId);

        insertRecord(minionInfo, "minions", connectionToMinionsDb);

        Integer minionId = getId(minionName, "minions", connectionToMinionsDb);

        Map<String, String> minionVillainInfo = new HashMap<>();
        minionVillainInfo.put("minion_id", "" + minionId);
        minionVillainInfo.put("villain_id", "" + villainId);

        insertRecord(minionVillainInfo, "minions_villains", connectionToMinionsDb);

        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);
    }

    private static boolean recordExists(String name, String tableName, Connection connectionToDb) throws SQLException {
        String sqlQuery = String.format("SELECT * FROM %s WHERE name = '%s'", tableName, name);
        PreparedStatement preparedStatement = connectionToDb.prepareStatement(sqlQuery);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    /*
    Type of map is not important. INSERT INTO table_name(column1, column2) VALUES(value1, value2)
    has the same effect as INSERT INTO table_name(column2, column1) VALUES(value2, value1)
     */
    private static void insertRecord //not exactly a universal way of doing an insert, but kinda works
            (Map<String, String> columnValue, String tableName, Connection connectionToDb) throws SQLException {
        String columns = String.join(", ", columnValue.keySet());
        String values = String.join(", ", columnValue.values());

        String sqlQuery = String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columns, values);
        PreparedStatement updateStatement = connectionToDb.prepareStatement(sqlQuery);
        updateStatement.executeUpdate();
    }

    private static String surroundWithApostrophes(String word) {
        return "'" + word + "'";
    }

    private static Integer getId(String name, String tableName, Connection connectionToDb) throws SQLException {
        String sqlQuery =
                String.format("SELECT * FROM %s WHERE name = '%s'", tableName, name);

        PreparedStatement retrieveStatement = connectionToDb.prepareStatement(sqlQuery);

        ResultSet resultSet = retrieveStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }
}
