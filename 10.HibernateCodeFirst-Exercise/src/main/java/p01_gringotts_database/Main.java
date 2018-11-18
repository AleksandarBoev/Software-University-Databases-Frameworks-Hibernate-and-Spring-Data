package p01_gringotts_database;

import p01_gringotts_database.entities.Gringott;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory gringottsEntityManagerFactory =
                Persistence.createEntityManagerFactory("gringotts_persistence");
        /*
        Note: If you want to add more and more data, but don't want the previous data wiped every time change
        <property name = "hibernate.hbm2ddl.auto" value="create"/> to
        <property name = "hibernate.hbm2ddl.auto" value="update"/>
         */
        EntityManager gringottsEntityManager = gringottsEntityManagerFactory.createEntityManager();

        Gringott gringott1 = new Gringott("Boev", 23);

        Timestamp gringott2DepositStartDate = Timestamp.valueOf("2012-09-28 09:00:00");
        Timestamp gringott2DepositExpirationDate = Timestamp.valueOf("2018-09-28 09:00:00");

        Gringott gringott2 = new Gringott("Pesho", "Peshov", "Some random guy", 22, "Dumbledor", 5, "Slytherin",
                gringott2DepositStartDate, BigDecimal.valueOf(2500), BigDecimal.valueOf(1500), BigDecimal.valueOf(200),
                gringott2DepositExpirationDate, false);

        gringottsEntityManager.getTransaction().begin();

        gringottsEntityManager.persist(gringott1);
        gringottsEntityManager.persist(gringott2);

        gringottsEntityManager.getTransaction().commit();
        gringottsEntityManager.close();
        gringottsEntityManagerFactory.close();
    }
}
