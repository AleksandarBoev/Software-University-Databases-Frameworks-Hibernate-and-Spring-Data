package app;

import app.entities.User;
import app.orm.Connector;
import app.orm.EntityManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException {
        Connector.createConnection("root", "", "minions_db");
        EntityManager<User> entityManager = new EntityManager<>(Connector.getConnection(), User.class);
    }
}


