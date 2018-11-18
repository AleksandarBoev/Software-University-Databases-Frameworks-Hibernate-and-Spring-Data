package p02_sales_database;

import p02_sales_database.entities.Customer;
import p02_sales_database.entities.Product;
import p02_sales_database.entities.Sale;
import p02_sales_database.entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory salesEntityManagerFactory =
                Persistence.createEntityManagerFactory("sales_persistence");
        EntityManager salesEntityManager = salesEntityManagerFactory.createEntityManager();

//        fillWithData(salesEntityManager);

        Customer customer = salesEntityManager.find(Customer.class, 1L);
        for (Sale sale : customer.getSales()) {
            System.out.println(sale);
        }

        salesEntityManager.close();
        salesEntityManagerFactory.close();
    }

    private static void fillWithData(EntityManager salesEntityManager) {
        salesEntityManager.getTransaction().begin();

        Customer customer1 = new Customer("Aleksandar", "0123456789");
        Customer customer2 = new Customer("Pesho", "0123456789");

        Product product1 = new Product("Bananas", 6, BigDecimal.valueOf(2.50));
        Product product2 = new Product("Apples", 4, BigDecimal.valueOf(1.80));

        StoreLocation storeLocation = new StoreLocation("Sofia, 1100 Bulevard str");

        Sale sale1 = new Sale(product1, customer1, storeLocation, Date.valueOf("2018-05-12"));
        Sale sale2 = new Sale(product2, customer1, storeLocation, Date.valueOf("2018-05-13"));

        Sale sale3 = new Sale(product1, customer2, storeLocation, Date.valueOf("2018-06-03"));

        salesEntityManager.getTransaction().begin();

        salesEntityManager.persist(customer1);
        salesEntityManager.persist(customer2);
        salesEntityManager.persist(product1);
        salesEntityManager.persist(product2);
        salesEntityManager.persist(storeLocation);
        salesEntityManager.persist(sale1);
        salesEntityManager.persist(sale2);
        salesEntityManager.persist(sale3);

        salesEntityManager.getTransaction().commit();
    }
}
