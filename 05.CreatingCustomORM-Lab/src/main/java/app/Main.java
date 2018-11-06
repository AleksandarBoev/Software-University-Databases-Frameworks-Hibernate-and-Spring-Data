package app;

import app.entities.User;
import app.orm.Connector;
import app.orm.EntityManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException {
        Connector.createConnection("root", "", "minions_db");
        EntityManager<User> entityManager = new EntityManager<>(Connector.getConnection(), User.class);

        Iterable<User> allUsers = entityManager.find();
        for (User user : allUsers) {
            System.out.println(user);
        }
        System.out.println();

        Iterable<User> specificUsers = entityManager.find("age >= 22");
        for (User user : specificUsers) {
            System.out.println(user);
        }
        System.out.println();

        System.out.println(entityManager.findFirst());
        System.out.println(entityManager.findFirst("id = 3"));


        User user = new User("Last try", 400);
        User existingUser = entityManager.findFirst();
        existingUser.setAge(9999);
        existingUser.setUserName("Yesssss");
        System.out.println(entityManager.persist(existingUser));
        System.out.println(entityManager.persist(user));
    }
}
