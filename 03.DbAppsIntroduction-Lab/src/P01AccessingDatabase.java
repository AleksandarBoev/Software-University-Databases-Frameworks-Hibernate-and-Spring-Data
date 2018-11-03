import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class P01AccessingDatabase {
    public static void main(String[] args) throws SQLException, IOException {
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connectionToDb = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/soft_uni", credentials
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Double salaryIndicator = Double.parseDouble(reader.readLine());
        reader.close();
        PreparedStatement sqlQuery = connectionToDb.prepareStatement(
                "SELECT first_name, last_name \n" +
                        "FROM employees \n" +
                        "WHERE salary > ?"
        );
        sqlQuery.setDouble(1, salaryIndicator);
        ResultSet resultSet = sqlQuery.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
        }

        connectionToDb.close();
    }
}
