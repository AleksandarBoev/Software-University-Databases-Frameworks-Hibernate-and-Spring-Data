package app;

import app.contracts.Executable;
import app.engines.Engine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("soft_uni-persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Executable engine = new Engine(entityManager);
        engine.execute();

        entityManager.close();
        entityManagerFactory.close();
    }
}
