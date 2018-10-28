package apps;

import java.sql.*;
import java.util.Properties;

public class EngineImpl implements Engine{
    private Connection connection;

    public EngineImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() throws SQLException {
        this.getVillainsName(10);
    }

    private void getVillainsName(int minimalNumberOfMinions) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(
                "SELECT *" +
                        "FROM minions;"
        );

//        preparedStatement.setString(); //when using setString it adds ["]...["] on both sides of the blabla
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }


    }
}
